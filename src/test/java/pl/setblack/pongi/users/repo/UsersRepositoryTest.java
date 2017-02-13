package pl.setblack.pongi.users.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.setblack.pongi.users.api.RegUserStatus;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class UsersRepositoryTest extends  UserRepositoryBase{

    @BeforeEach
    public void initRepo() {
        this.usersRepository = new UsersRepositoryInMemory();
    }

}

class UserPersistentRepoText extends UserRepositoryBase {
    private final Path testRepoPath = Paths.get("target/airomem/test");
    private UsersRepoES persistentRepo;
    @BeforeEach
    public void initRepo() {
        this.persistentRepo = new UsersRepoES(testRepoPath);
        this.usersRepository = this.persistentRepo;

    }

    @AfterEach
    public void deleteRepo() throws IOException{
        this.persistentRepo.close();
        Files.walkFileTree(testRepoPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }



}

class UserRepositoryBase {

    protected  UsersRepository usersRepository;

    @Test
    public void shouldRegisterUserOnce() {
        final RegUserStatus result = this.usersRepository.addUser( "irreg" , "baaaa");
        assertThat ( result.ok, is( true));
    }

    @Test
    public void shouldRegisterUserOnlyOnce() {
        this.usersRepository.addUser("irreg", "bbbb");
        final RegUserStatus result = this.usersRepository.addUser( "irreg" , "baaaa");
        assertThat ( result.ok, is( false));
    }

    @Test
    public void shouldLoginAfterRegister() {
        this.usersRepository.addUser("irreg", "bbbb");
        final boolean result = this.usersRepository.login( "irreg" , "bbbb");
        assertThat ( result, is( true));
    }

    @Test
    public void shouldFailLoginWithWrongPassword() {
        this.usersRepository.addUser("irreg", "bbbb");
        final boolean result = this.usersRepository.login( "irreg" , "baaaa");
        assertThat ( result, is( false));
    }

}