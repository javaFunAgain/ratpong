# ratpong
This is POUNK game server implementation which is  network version of a classic PONG. 

For client code go to : https://github.com/jarekratajski/scalajspounk

# Runing?
Just call ```gradle run ```
Navigate to http://localhost:9000

As a user you can register with user and password.
After that you can login.
You can create new game and enter game... 
but once another player joins it. Yes you need 2 players,  
you may open second browser window and register  a second player.

Controls: 
 - Q move paddle up,
 - A move paddle down,

# Purpose
The goal of this project is  to prepare clean Java example of non blocking server architecture.
System uses as little mutability as possible, no magic frameworks, application servers
 and simply starts with main method ( as Fat Jar).

For a moment system uses such technologies:
- [Ratpack](https://ratpack.io/) providing  REST server library,
- [JavaSlang](http://www.javaslang.io/) for immutable data structures (vavr :-) ),
- Airomem / prevayler for simple persistence
- Junit5 for simple tests,

 
 
Notice that system does not use any special dependency injection frameworks or containers.
It is 2017 -  we do not need it anymore! :smile:

# Legal issue
Please,  bear in mind that PONG is a registered trademark that probably  belongs to [Atari SA](https://en.wikipedia.org/wiki/Atari,_SA).

# Architecture
 System is divided into 3 main modules. 
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
Code does not use any dependency injection container. For it is 2017.
Once you use modern web server such as Ratpack you do not have technical limitations  that happen with Servlet architecture that make use of dependency injection by container handy.
You are in full control of our objects creation and you can use this power.
:metal:


It does not mean there is no *dependency injection* working. It is. Only done the easy way: with constructor. (Notice that if you follow 
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

In order to not get lost in "new" hell there is a factory which handles some modules defaults.
See [ScoresModule](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/scores/ScoresModule.java). This is exactly one place which decides explicitly what is  default persistence etc.
This code is more or less same as you would define in spring-beans.xml or with **@Component** annotations. This time however
fully type safe, debuggable, testable. And without magic.

You can check the same concept working with more complicated classes as [GamesService/GameModule](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/games/GamesModule.java)
More dependencies? No problem. Use java to control  them all. :beers:
 
 ## Service 
 It is easy to define REST api.
 Lets talk about Users.
 
 Thare are 2 operations there.
 **POST /users/USER_ID**   -register new user  by POSTing data (password)
 **POST /sessions/USER_ID** - login user by posting password (it is POST because we create session)
 
 There is usersApi method in [UsersService](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/UsersService.java) which basically defines how to handle both operations.
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

Then   real **addUser** implementation can be written:
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
 one should not block processing of request (by calling and waiting for IO ). 
 So if you want to ask the database and then wait for result you are generally destroying the whole concept of non blocking architecture.
 :boom:
 
 So how to work with that? 
 
 The answer are Promises (which are some form of Futures that you could have probably heard about). 
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
 (Btw. this operation can be easily extracted) 
 
 So: you know how to render Promise (or CompletableFuture) - but how to get it?
 
 Here comes to help the class called [UserRepositoryProcessor](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepositoryProcessor.java). The responsibility of this class is to
 cooperate with a blocking persistence engine and delegate processing to other threads so that the request processing thread is not blocked.
  
You may wonder: *what sense does  it make?* -  not blocking the request thread but instead to block some other thread... where is the point?

That is good point indeed. If you had some kind of nonblocking DB such as Cassandra or even MongoDB you could leverage that in be truly non blocking way.
But probably you do not have. Still nothing wrong happens! It is possible to use Blocking IO with non blocking server and still get some gain.

What you can do (win) is the control of your application threads! Imagine 5000 users trying to get access to the rest service simultaneously.
In a classical (blocking) architecture it would mean 5000 threads are created, all of them making connection to database, waiting for answer, holding memory.  
This does not sound good.  Imagine what your database feels.

 As an experienced human you know that it is typically better to do
 tasks one by one - than to start thousands of them concurrently. 
     
 This is exactly what can be done with blocking DB. You can limit how many concurrent queries  (or generally operations) you perform and
 simply queue all the rest. This is what "Processor" class does. 
 It uses very nice tool from standard Java lib called [Executor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html). Simply create an executor and tell how many threads should it use (lets say one!). 
 Then just queue all subsequent operations using it: 
     
     ```
     writesExecutor.execute( ()-> {
                 result.complete(this.usersRepository.addUser(login, pass));
             });
    ```         
 See? That executor operation returns **CompletableFuture** - and this is exactly what we need.       
Notice also that this is called writesExecutor because we are only doing so with writes.
Read operations are called instantly.... why?
This is because in that case you have (almost) non blocking datasource (pervayler./ airomem) which is performing reads (only reads) without blocking.
If you worked with SQL database such as Oracle you would have to queue both reads and writes.
     
 
 
 
 ## Persistence
 It is good moment to introduce persistence.
 First persistence mechanism proposed here is [Airomem](https://github.com/airomem) (which is basically only Java 8 wrapper for [Prevayler](http://prevayler.org/).)
 To read more about it please go to  [Prevayler  page](http://prevayler.org/) 
 Or watch one of my presentations :smile:
 
 For a moment it is enough to say that Airomem is a persistence that you have dreamed about - you just store your objects as 
 Java objects (maps, lists, whatever) and they are stored in the background.
 
 Simply design a system as if there was no persistence and everything we want is in in RAM (in fact it is).
 This is exactly what the class [UserRepositoryInMemory](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepositoryInMemory.java) does.
 ```
 public class UsersRepositoryInMemory implements UsersRepository, Serializable {
     private static final long serialVersionUID = 1L;
 
     private volatile HashMap<String, UserData> allUsers = HashMap.empty();
 

 ```
See **HashMap** there ? It is exactly your database :-). And no worry - even if you restart, or kill program the data will be persisted.
That is no magic. That is how *Prevayler* (Prevalence) works.

OK. I lied a little to you. :smiling_imp: You need to do one more step. You have to wrap your repo in some
other class. See [UserRepoES](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/users/repo/UsersRepoES.java).
As you can find out -  it only delegates all operations to InMemoryRepo - 
but this does the trick.

 ## JOOQ
  
  If you are somehow sick and you still need to use SQL databases there is also a solution for you.
  Just check this branch:
  [h2 Database](https://github.com/javaFunAgain/ratpong/tree/h2.
  Check the class:
  [ScoresReopository](https://github.com/javaFunAgain/ratpong/blob/h2/src/main/java/pl/setblack/pongi/scores/repo/ScoresRepositorySQL.java).
  
  Why JOOQ not JPA. There are a lot of reasons but maybe just listen to one of the videos
  of JOOQ creator: 
  (Get Back in Control of Your SQL)[https://www.youtube.com/watch?v=7bqmj-3DODE]

 
 ## Testing
 
Testing Ratpack contrary to lot of container is just awesome. Imagine that you test your HTTP web services just with HTTP.
You create request, add some headers (if needed) and then you do real call with HTTP!.
How cool - you can indeed  rely on such tests!

But isn't it slow then? ... what if I tell you that starting Ratpack with services takes sth like 16 ms on an average PC...
This means in one second You can start 50 times your server (how great!).
But isn't then problem with database - such server would use real database (or whatever persistence you use).  
Not really because we've used **dependency injection**.

See [UsersServiceTest](https://github.com/javaFunAgain/ratpong/blob/master/src/test/java/pl/setblack/pongi/users/UsersServiceTest.java) 
For tests there is in memory repository used.
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

     private UsersService initService (){
            return new UsersService(new UsersRepositoryInMemory(),
                    new SessionsRepo(clock));
        }

```

 
 ## Immutability
 In this project almost every class is immutable. It means for instance that the state of the game
 (see class GameState) is an immutable class. Every time player moves 
 or ball moves there is in fact new state (new instance) of  the game created.
 
 This makes testing and reasoning about code way easier.
 There is an @Immutable annotation used to document such classes.
 
 There are few classes that are mutable. Generally databases are mutable.
 So the classes that keep data state like GameRepositoryInMemory contain mutable
 fields. See however that still they use Immutable collections in order to limit number of places
 where mutability happens and make it more explicit in code.
 
 For example it uses ```javaslang.collection.HashMap``` to store game states (allGamesState).
 Thus every new gamestate is clearly visible in code:
 ```
      final GameState newState= game.push(this.clock.millis(), this.random);
            this.allGamesState = this.allGamesState.put(gameUUID, newState);
```

 
 Second place where mutability can be found is for technical reasons in non  business code
 like handling of websockets (the collection of all sockets is  mutable).
 
 
 
 ## WebSockets
 

For Websockets usage see [GameService](https://github.com/javaFunAgain/ratpong/blob/master/src/main/java/pl/setblack/pongi/games/GamesService.java)
 streamGame method.
```
private Handler streamGame(Chain chain) {
        return ctx -> {
            final String gameId = ctx.getPathTokens().get("id");
            final Option<Flowable<GameState>> gsOpt = Option.of(this.gamesFlow.get(gameId));
            gsOpt.forEach(gsFlow -> {
                final Flowable<String> stringFlow = gsFlow

                        .map(val -> {
                            final String result =
                                    chain.getRegistry()
                                            .get(ObjectMapper.class)
                                            .writeValueAsString(val);

                            return result;
                        });


                WebSockets.websocketBroadcast(ctx, stringFlow);
            });
        };

    }
```

As you see websocket stream is simply a RxJava 2.0 <i>Flowable</i>!

Each flowable contains changes of game state which are calulated
at regular intervals - see createFlow method.
