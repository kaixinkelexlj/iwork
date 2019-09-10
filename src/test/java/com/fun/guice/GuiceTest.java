package com.fun.guice;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Named;

/**
 * @author xulujun 2019/09/10
 * https://www.jianshu.com/p/a648322dc680
 */
public class GuiceTest {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new AppModule());
    App app = injector.getInstance(App.class);
    app.out();
  }

  public interface UserService {

    void process();
  }

  public static class UserServiceImpl implements UserService {

    @Override
    public void process() {
      System.out.println("user service impl");
    }
  }

  public interface Worker {

    void work();
  }

  public static class AppModule extends AbstractModule {

    @Override
    protected void configure() {
      bind(UserService.class).to(UserServiceImpl.class);
      bind(String.class).annotatedWith(Names.named("url"))
          .toInstance("http://www.xingxing.com");
    }

    @Provides
    @Baba
    public Worker provideWorker() {
      return new Worker() {
        @Override
        public void work() {
          System.out.println("baba");
        }
      };
    }


  }

  @BindingAnnotation
  @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Baba {

  }


  public static class App {

    private UserService userService;
    private String url;
    private Worker worker;

    @Inject
    public App(UserService userService, @Named("url") String url, @Baba Worker worker) {
      this.userService = userService;
      this.url = url;
      this.worker = worker;
    }

    public void out() {
      userService.process();
      System.out.println("url:" + url);
      worker.work();

    }
  }

}
