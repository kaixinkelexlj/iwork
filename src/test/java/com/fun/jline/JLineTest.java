package com.fun.jline;

import com.fun.cmd.CmdExecutors;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xulujun
 * @date 2018/06/20
 */
public class JLineTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(JLineTest.class);

  public static void main(String[] args) throws Exception {
    try (Terminal terminal = TerminalBuilder.builder()
        .name("fun")
        .system(true)
        .nativeSignals(true)
        //.streams(System.in, System.out)
        .signalHandler(Terminal.SignalHandler.SIG_IGN)
        .build()) {

      LineReader reader = LineReaderBuilder.builder()
          .terminal(terminal)
          .build();
      reader.setOpt(LineReader.Option.AUTO_FRESH_LINE);

      String prompt = "fun>";
      while (true) {
        String line;
        try {
          line = reader.readLine(prompt);
          if ("exit".equalsIgnoreCase(line)) {
            System.exit(0);
          }
          LOGGER.info(line);
          CmdExecutors.newBashExecutor().exec(System.out, "bash", "-c", line);
        } catch (UserInterruptException e) {
          // Ignore
        } catch (EndOfFileException e) {
          break;
        }

      }

    }
  }
}
