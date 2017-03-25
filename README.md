# ratpong
This is POUNK game server implementation which is  network version of a classic PONG. 

For client code go to : https://github.com/jarekratajski/scalajspounk

# Runing?
Just call ```gradle run ```
Navigate to http://localhost:9000

As a user you can register with user and password.
After that you can login.
You can create new game and enter game... 
once another player joins it.  
You may open second browser window and register second player.

Controls: 
 - Q move paddle up,
 - A move paddle down,


# Purpose
The goal of this project is  to prepare clean Java example of non blocking server architecture.
System uses as little mutability as possible, no magic frameworks, application servers
 and simply starts with main method ( as Fat Jar).

For a moment system uses such technologies:
- [Ratpack](https://ratpack.io/) providing  REST server library,
- [JavaSlang](http://www.javaslang.io/) for immutable data structures,
- Airomem / prevayler for simple persistence
- Junit5 for simple tests,

 
 
Notice that system does not use any special dependency injection frameworks or containers.
It is 2017 -  we do not need it anymore! :smile:

# Legal issue
Please,  bear in mind that PONG is a registered trademark that probably  belongs to [Atari SA](https://en.wikipedia.org/wiki/Atari,_SA).
This source code is created only for educational purposes and you may use it according to provided license.

 # Architecture
 System is divided in 3 main modules. 
 - [users](https://github.com/javaFunAgain/ratpong/tree/master/src/main/java/pl/setblack/pongi/users) - user registration and login/session logic 
 - [games](https://github.com/javaFunAgain/ratpong/tree/master/src/main/java/pl/setblack/pongi/games) - created games list, states of games 
 - [scores](https://github.com/javaFunAgain/ratpong/tree/master/src/main/java/pl/setblack/pongi/users) - registered scores
 
Typical module consists of:
  - [service class](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/UsersService.java) - which defines REST methods
  -[api package](https://github.com/javaFunAgain/ratpong/tree/master/src/main/java/pl/setblack/pongi/users/api) that defines JSON structures for client and server
  - [module class](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/UsersModule.java) - which contains configuration
  - [repositories](https://github.com/javaFunAgain/ratpong/tree/master/src/main/java/pl/setblack/pongi/users/repo) - various repositories implementations
 
# Concepts
 
## Dependency injection
We do not use any dependency injection container. For it is 2017.
Once we use modern web server such as Ratpack we do not have technical limitations  that happen with Servlet architecture that make use of dependency injection by container handy.
We are in full control of our objects creation and we can use this power.
:metal:


But we have *dependency injection* working - just done the easy way: with constructor. (Notice that if you follow 
Oliver Gierke advice [field injection evil](http://olivergierke.de/2013/11/why-field-injection-is-evil/)  you get almost same code...)


Check for instance [ScoresService](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/scores/ScoresService.java) class. 
It has one dependency ScoresRepositoryProcessor which
allows us to inject different persistence engine if needed (or for tests). 
```
     public ScoresService(ScoresRepositoryProcessor nonBlockingRepo) {
        this.nonBlockingRepo = nonBlockingRepo;
    }
```
Btw.: ScoresRepositoryProcessor has indeed further dependencies... (but this is not the problem).

In order to not get lost in "new" hell we simply create a factory which handles some modules defaults.
See [ScoresModule](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/scores/ScoresModule.java). This is exactly one place where we decide explicitly what is our default persistence etc.
This code is more or less same as you would define in spring-beans.xml or with **@Component** annotations. This time however
fully type safe, debuggable, testable. And without magic.

See the same concept working with more complicated classes as [GamesService/GameModule](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/games/GamesModule.java)
More dependencies? No problem to control  them all. :beers:
 
 ## Service 
  It is easy to define REST api.
 Lets talk about Users.
 We will have 2 operations there.
 **POST /users/USER_ID**   -register new user  by POSTing data (password)
 **POST /sessions/USER_ID** - login user by posting password (it is POST because we create session)
 
 So there is usersApi method in [UsersService](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/UsersService.java) which basically defines how to handle both operations.
 See how easy we do it with lambdas: :sunglasses:
 
 ```
 
 public Action<Chain> usersApi() {
         return apiChain -> apiChain
                 .prefix("users", users())
                 .prefix("sessions", sessions());
 
     }
 
     private Action<Chain> users() {
         return chain -> chain
                 .post(":id", addUser());
 
     }

```

Then we write  real **addUser** implementation:
```

private Handler addUser() {
        return ctx -> {
            final String userId = ctx.getPathTokens().get("id");
            ctx.parse(NewUser.class).then(
                    newUser -> {
                        final Promise result = Promise.async(
                                d -> d.accept(usersRepo.addUser(userId, newUser.password).thenApply(Jackson::json)
                                ));
                        ctx.render(result);
                    }
            );
        };

```
 See how simple is to convert JSON from input (**ctx.parse**) and then our result 
  to output JSON (**Jackson::json**).
  
 ### Promises
 One thing that may look unfamiliar in the code above is this **Promise** thing. :confused:
 Ratpack is a non blocking server. What does it mean:
 we should not block processing of request (by calling and waiting for IO ). 
 So if we ask the database and then wait for result we are generally destroying the whole concept of non blocking architecture.
 :boom:
 
 So how to work with that? 
 
 The answer are Promises (which are some form of Futures that you could have heard). 
 Instead of just doing  the blocking thing we return Promise object that will be completed somewhere in the future.
 Possibly when result from our database is read.
 Ratpack makes life easy here providing handy implementation of Promise.
 ctx.render accepts generally a String in case we know how to answer  or  a Promise which will be resolved somewhere in the future 
 (which is more likely).
 
 So what is this? 
 ```
 final Promise result = Promise.async(
                                 d -> d.accept(usersRepo.addUser(userId, newUser.password).thenApply(Jackson::json)
                                 ));

```
 This is in fact conversion between JAVA  promise called **CompletableFuture** and Ratpack **Promise**. (Ratpack was created before CompletableFuture  was defined in Java API).
 Btw. this operation can be easily extracted. (TODO)
 
 So: we know how to render Promise (or CompletableFuture) - but how to get it?
 
 Here comes to help class called [UserRepositoryProcessor](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepositoryProcessor.java). The responsibility of this class is to
 cooperate with some blocking persistence engine and delegate processing to other threads so that the request processing thread is not blocked.
  
You may wonder: *what sense does  it make?* - we are not blocking the request thread but instead we block some other thread... where is the point?
Good point. If we had some kind of nonblocking DB such as Cassandra or even MongoDB we could leverage that in be truly non blocking way.
But we do not have. Still nothing wrong happens! It is possible to use Blocking IO with non blocking server and still get some gain.
What we can win is the control of our threads! Imagine 5000 users trying to get access to our page simultaneously.
In a classical (blocking) architecture it would mean 5000 threads are created, all of them making connection to database, waiting for answer, holding memory.  This does not sound good. 
Try to fell what does it mean to your database. Have you ever tried to copy large folder with lot of small files to your USB storage? Did it help if you started to 
copy 3 such folders at the same time to that one unlucky USB?  So something you have probably learned with windows is to *Queue* such operations.   
You will start copying second folder once the first one is finished.
     
 This is exactly what can be done with blocking DB. We can limit how many concurrent queries  (or generally operations) we do and
 simply queue all the rest. This is what "Processor" class does. 
 It uses very nice tool from Java classes called [Executor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html). We create executor and tell how many threads should it use (lets say one!). Then we simply 
     queue operations with it: 
     
     ```
     writesExecutor.execute( ()-> {
                 result.complete(this.usersRepository.addUser(login, pass));
             });
    ```         
 See? That executor operation returns **CompletableFuture** - and this is exactly what we need.       
Notice also that this is called writesExecutor because we are only doing so with writes.
Read operations are called instantly.... why?
This is because in that case we have (almost) non blocking datasource (pervayler./ airomem) which is performing reads without blocking.
(Which is however not the case when it comes to writes). If we worked with SQL database such as Oracle we would have to queue both reads and writes.
     
 
 
 
 ## Persistence
 So maybe it is good moment to introduce persistence.
 Our first persistence mechanism is [Airomem](https://github.com/airomem) (which is basically only Java 8 wrapper for [Prevayler](http://prevayler.org/).)
 To read more about it please go to  [Prevayler  page](http://prevayler.org/) 
 Or watch one of my presentations.
 
 Here it is enough to say that Airomem is a persistence that you have dreamed about - you just store your objects as 
 Java objects (maps, lists, whatever) and they are magically stored in the background.
 
 We simply design a system as if there was no persistence and everything we want is in in RAM (in fact it is).
 This is exactly what the class [UserRepositoryInMemory](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepositoryInMemory.java) does.
 ```
 public class UsersRepositoryInMemory implements UsersRepository, Serializable {
     private static final long serialVersionUID = 1L;
 
     private volatile HashMap<String, UserData> allUsers = HashMap.empty();
 

 ```
See **HashMap** there ? It is exactly our database :-). And no worry - even if you restart, or kill program the data will be persisted.
That is no magic. That is how *Prevayler* (Prevalence) works.

OK. I lied a little to you. :smiling_imp: We need to do one more step. We have to wrap our repo in some
other class. See [UserRepoES](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepoES.java).
As you can find out it only delegates all operations to our InMemoryRepo - 
but this do the trick.
 
 ## Testing
 
Testing Ratpack contrary to lot of container is just awesome. Imagine that you test your HTTP web services just with HTTP.
You create request, add some headers (if needed) and then you do real cal with HTTP!.
How cool - you can indeed  rely on such tests!

But isn't it slow then? ... what if I tell you that starting Ratpack with services takes sth like 16 ms on an average PC...
This means in one second You can start 50 times your server (how great!).
But isn't then problem with database - such server would use real database (or whatever persistence you use).  
Not really because we've used **dependency injection**.

See [UsersServiceTest](https://github.com/javaFunAgain/ratpong/blob/master/src/test/java/pl/setblack/pongi/users/UsersServiceTest.java) 
we create there in memory Repository (for tests) and then start Ratpack with usersService passing 
 In memory repository as an implementation..
```

    @Test
    public void shouldRegisterUser() throws Exception {
       prepareServer().test(
                testHttpClient -> {
                    final String response = testHttpClient.requestSpec(rs ->
                            rs.headers( mh -> mh.add("Content-type", "application/json"))
                            .body( body -> body.text("{\"password\": \"upa\"}")))
                            .post("/api/users/aa")
                            .getBody().getText();
 
                        ...                  
                }
        );
    }

    private EmbeddedApp prepareServer() {
        final UsersService usersService = initService();
        return EmbeddedApp.fromServer(
                Server.createUnconfiguredServer(usersService.usersApi())
        );
    }

```

 
 
 ## Immutability
 
 
 ## WebSockets
 




