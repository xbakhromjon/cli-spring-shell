package uz.bakhromjon.clispringshell.generate;


import jakarta.validation.constraints.Size;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.logging.Logger;

import static java.lang.String.format;

@ShellComponent
public class SSHCommand {
    Logger log = Logger.getLogger(SSHCommand.class.getName());

    @ShellMethod(key = "my-ssh", value = "connect to remote server")
    public void m1(@ShellOption(value = "remote-server") String remoteServer) {
        log.info(format("Logged to machine 2 '%s'", remoteServer));
    }

    @ShellMethod(value = "connect to remote server")
    public void m2(@ShellOption(value = "--s", defaultValue = "default-server") String remoteServer) {
        log.info(format("Logged to machine '%s'", remoteServer));
    }

    @ShellMethod(value = "add keys")
    public void sshAdd(@ShellOption(value = "--k", arity = 2) String[] keys) {
        log.info(format("Adding keys '%s' '%s'", keys[0], keys[1]));
    }


    @ShellMethod(value = "sign in")
    public void sshLogin(@ShellOption(value = "--r") Boolean rememberMe) {
        log.info(format("remember me option is '%s'", rememberMe));
    }

    @ShellMethod(value = "ssh agent")
    public void sshAgent(
            @ShellOption(value = "--a")
            @Size(min = 2, max = 10) String agent) {
        log.info(format("adding agent '%s'", agent));
    }


    private boolean signedIn;

    @ShellMethod(value = "sign in")
    public void signIn() {
        this.signedIn = true;
        log.info("Signed In!");
    }

    @ShellMethod(value = "sign out")
    public void signOut() {
        this.signedIn = false;
        log.info("Signed out!");
    }


    @ShellMethod(value = "Change password")
    public void changePass(@ShellOption(optOut = true) String newPass) {
        log.info(format("Changed password to '%s'", newPass));
    }

    @ShellMethodAvailability({"sign-out", "change-pass"})
    public Availability signOutAvailability() {
        return signedIn ?
                Availability.available() : Availability.unavailable("Must be signed in first");
    }

}
