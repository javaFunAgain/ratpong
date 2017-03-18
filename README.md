# ratpong


This is sample implementation of classic game PONG. Or more precisely REST server that
support internet play in PONG.

For client code go to : https://github.com/jarekratajski/scalajspounk


# Runing?
Just call ```gradle run ```
And the navigate to localhost:9000


# Purpose
The goal of this project is  to prepare clean Java example of non blocking server architecture.
System uses as little mutability as possible, does not depend on huge frameworks etc.
Code should be concise (as for java standards).

For a moment system uses such technologies:
- Ratpack providing  REST server library,
- JavaSlang for immutable data structures,
- Airomem(prevayler) for simple persistence
- Junit5 for simple tests,


 
 
Notice that system does not use any special dependency injection frameworks or containers.
It is 2017 and we do not need it.

# Legal
Pleas bear in mind that PONG is a registered trademark that belongs to Atari Corporation.
This source code is created only for educational purposes and you may use it according to provided license.

# TODO
- Use JOOQ for simple SQL persistence,
- better exception handling and diagnostics,
 
 
 # Conecepts
 ## Dependency injection
 We do not use any dependency injection container. First it is not needed, second it is 2017.
 Once we use modern Web Server such as Ratpack we do not have any technical problems (we fully control creation of
 our objects) that happen with Servlet architecture that make use of dependency injection by container handy.

CDI is a smell.

But we have dependency injection working - just done the easy way with constructor. (Notice that if you follow 
Oliver Gierke advice http://olivergierke.de/2013/11/why-field-injection-is-evil/  you get same code...)


Check for instance ScoresService class. It has one dependency ScoresRepositoryProcessor which
allows us to inject different persistence engine if needed (or for tests). ScoresRepositoryProcessot has indeed further
dependency.

In order to not get lost in "new" hell we simply create a factory which handles some modules defaults.
Sees ScoresModule. This is exactly one place where we decide explicitly what is our default persistence etc.
This is more or less same as You define in Spring-beans.xml or with @Component annotations. This time however
fully type safe, debuggable, testable. And without magic.


See the same concept working with more complicated classes as GamesService.
More dependencies  - still no problem to control all of them.

 
 ## Service 
 
 Notice how simple it is to define new REST api.
 Lets talk about Users.
 We will have 2 operations there.
 POST /users/USER_ID   -register new user  by POSTing data (password)
 POST /sessions/USER_ID - login user by posting password (it is POST because we create session)
 
 So there is usersApi method in UsersService which basically defines how to handle both operations.
 See how easy we do it with lambdas:
 
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

Then real addUser operation is just what we want to write:
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
 See how simple is to convert JSON from input (ctx.parse) and then our result 
  to output JSON (Jackson::json).
  
 ### Promises
 One thing that may look unfamiliar in the code above is this Promise.
 Basically Ratpack is a non blocking server. What does it mean:
 we should not block processing of request calling IO. 
 So if we ask the database and then wait for result we are generally destroying the whole concept of non blocking architecture.
 
 So how to work with that? 
 
 The answer are Promises (which are some form of Futures that you could have heard). 
 Instead of just doing  the blocking thing we return Promise object that will be completed somewhere in the future.
 Possibly when result from our database is read.
 Ratpack makes life easy here providing handy implementation of Promise.
 ctx.render accepts generally a String in case we know how to answer  or, a Promise which will be resolved somewhere in the future 
 (which is more likely).
 
 So what is this? 
 ```
 final Promise result = Promise.async(
                                 d -> d.accept(usersRepo.addUser(userId, newUser.password).thenApply(Jackson::json)
                                 ));

```
 This is in fact conversion between JAVA  promise called CompletableFuture and Ratpack Promise. (Ratpack was created before CompletableFuture  was defined in Java API).
 Btw. this operation can be easily extracted. (TODO)
 
 So we know how to render Promise (or CompletableFuture) - but how to get it?
 
 Here comes to help class called UserRepositoryProcessor the responsibility of this class is to
 cooperate with some blocking persistence engine and delegate processing to other threads so that the request processing thread is not blocked.
  
You may wonder what sens it makes - we are not blocking the request thread but instead we block some other thread... where is the gain?
Good point. If we had some kind of nonblocking DB such as Cassandra or even MongoDB we could leverage that in be truly non bloicking.
But what if not. In fact nothing wrong happens  it is possible to use Blocking IO with non blocking server and still get some gain.
What we can win is the control of our threads. Imagine 5000 users trying to get access to our page simultaneously.
In a classical architecture it would mean 5000 threads are created, all of them making connection to database, waiting for answer, holding memory.  This does not sound good. 
Try to fill what does it mean to your database. have you ever tried to copy large folder with lot of smile files to your USB storage? Did it help if you started to 
copy 3 such folders at the same time to one unlucky USB?  So something you have probably learned with windows is to *Queue* such operations.   
You will start copying second folder once the first one is finished.
     
 This is exactly what can be done with blocking DB. We can limit how many concurrent queries  (or generally operations) we do and
 simply Queue all the rest. This is what "Processor" class does. 
 it uses very nice tool from JRE called Executor. We create executor and tell how many threads should it use (lets say one!). Then we simply 
     queue operations with 
     
     ```
     writesExecutor.execute( ()-> {
                 result.complete(this.usersRepository.addUser(login, pass));
             });
    ```         
 Notice that executor operation returns CompletableFuture - and this is exactly what we need.       
     Notice also that this is called writesExecutor because we are only doing so with writes.
     Read operations are called instantly.... why?
     This is because in that case we have (almost) non blocking datasource (pervayler./ airomem) which is performing reads without blocking.
     (This is however not the case of writes). If we worked with SQL database such as Oracle we would have to queu both reads and writes.
     
  
 
 
 
 ## Persistence
 So maybe it is good moment to introduce persistence.
 Our first persistence mechanism is Airomem (which is basically Prevayler.)
 To read about it please go to : 
 Or watch one of my presentations.
 
 To simplify a little bit Airomem is a peristence that you have dreamed about - You just store your objects in some 
 Java object (maps, lists, whatever) and they magically stored in background.
 So we simply design a system as if there was no persistence and everything we want we store in RAM.
 This is exactly what the class UserRepositoryInMemory does.
 ```
 public class UsersRepositoryInMemory implements UsersRepository, Serializable {
     private static final long serialVersionUID = 1L;
 
     private volatile HashMap<String, UserData> allUsers = HashMap.empty();
 

 ```
See HashMap there ? It is exactly our database :-). And no worry - even if you restart, or kill program the data will be persisted.
 That is no magic. That is how prevayler works.
 
 ## Testing
 
 You've probably seen lot of tests with Mockito. Maybe you believe that testing with mocks is exactly what You would want. In fact it is the opposite:
 the more you mock the more probable that you only test Mockito. And even if you do it perfectly with verify etc. it is still not good because there is a big possibility after such testsw 1 to 1 cover your implementation. 
 ANd there comes the problem - try to do refactoring .... you have to rewrite your tests. Is that what youy've wanted.
 
 So the lesson from many projects is tests functionality and try to to them little black box( at east gray :-)). You can check some internals but do not be to eager.
 
Testing Ratpack contrary to lot of container is just awesome. Imagine that you test your HTTP web services just with HTTP.
You create request, add some headers (if needed) and then you do real cal with HTTP!.
How cool - you can indeed  rely on such tests.

But isn't it slow then? ... what if I tell you that starting ratpack with services takes sth like 16 ms on an average PC...
This means in one second You can start 50 times your server (how great!).
But isn''t then problem with database - such server would use real Database (or whatever persistence you use).  
Not really because we've used dependency injection.
See UsersServiceTest 
we create there in memory Repository (for tests) and then start Ratpack with usersService passing 
 In memory repository as an implementation..
```

    @Test
    public void shouldRegisterUser() throws Exception {
       prepareServer().test(
                testHttpClient -> {
                    final Object response = testHttpClient.requestSpec(rs ->
                            rs.headers( mh -> mh.add("Content-type", "application/json"))
                            .body( body -> body.text("{\"password\": \"upa\"}")))
                            .post("/api/users/aa")
                            .getBody().getText();

                    System.out.println(response);
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
 




