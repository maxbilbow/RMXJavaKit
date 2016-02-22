package click.rmx.cmd;

/**
 * Created by Max on 22/02/2016.
 */
public interface Command<T>{
  String getCommand();

  String getDescription();

  String[] getArgs();

  <Ex> T invoke(String[] args, String aMessage, Ex aObject);

  @FunctionalInterface
  interface Execution<T,Ex>
  {
     T invoke(String aMessage, Ex aObject, String... args);
  }



}
