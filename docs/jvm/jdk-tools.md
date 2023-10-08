# [JDK Tool Specifications](https://docs.oracle.com/en/java/javase/21/docs/specs/man/index.html)
> 需要注意的是每个JDK版本工具可能不太一样，就算不同版本都有相同的工具其可选参数也可能不一样，更不用说不同发行版的JDK更是如此，此处基于Oracle JDK 21，其它版本请参考：[Java Platform, Standard Edition Documentation](https://docs.oracle.com/en/java/javase/index.html)。

### All Platforms
- [jar](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jar.html) - create an archive for classes and resources, and manipulate or restore individual classes or resources from an archive
- [jarsigner](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jarsigner.html) - sign and verify Java Archive (JAR) files
- [java](https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html) - launch a Java application
- [javac](https://docs.oracle.com/en/java/javase/21/docs/specs/man/javac.html) - read Java class and interface definitions and compile them into bytecode and class files
- [javadoc](https://docs.oracle.com/en/java/javase/21/docs/specs/man/javadoc.html) - generate HTML pages of API documentation from Java source files
- [javap](https://docs.oracle.com/en/java/javase/21/docs/specs/man/javap.html) - disassemble one or more class files
- [jcmd](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jcmd.html) - send diagnostic command requests to a running Java Virtual Machine (JVM)
- [jconsole](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jconsole.html) - start a graphical console to monitor and manage Java applications
- [jdb](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jdb.html) - find and fix bugs in Java platform programs
- [jdeprscan](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jdeprscan.html) - static analysis tool that scans a jar file (or some other aggregation of class files) for uses of deprecated API elements
- [jdeps](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jdeps.html) - launch the Java class dependency analyzer
- [jfr](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jfr.html) - parse and print Flight Recorder files
- [jhsdb](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jhsdb.html) - attach to a Java process or launch a postmortem debugger to analyze the content of a core dump from a crashed Java Virtual Machine (JVM)
- [jinfo](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jinfo.html) - generate Java configuration information for a specified Java process
- [jlink](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jlink.html) - assemble and optimize a set of modules and their dependencies into a custom runtime image
- **[jmap](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jmap.html)** - print details of a specified process
- [jmod](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jmod.html) - create JMOD files and list the content of existing JMOD files
- [jpackage](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jpackage.html) - package a self-contained Java application
- **[jps](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jps.html)** - list the instrumented JVMs on the target system
- [jrunscript](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jrunscript.html) - run a command-line script shell that supports interactive and batch modes
- [jshell](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jshell.html) - interactively evaluate declarations, statements, and expressions of the Java programming language in a read-eval-print loop (REPL)
- [jstack](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jstack.html) - print Java stack traces of Java threads for a specified Java process
- [jstat](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jstat.html) - monitor JVM statistics
- [jstatd](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jstatd.html) - monitor the creation and termination of instrumented Java HotSpot VMs
- [jwebserver](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jwebserver.html) - launch the Java Simple Web Server
- [keytool](https://docs.oracle.com/en/java/javase/21/docs/specs/man/keytool.html) - manage a keystore (database) of cryptographic keys, X.509 certificate chains, and trusted certificates
- [rmiregistry](https://docs.oracle.com/en/java/javase/21/docs/specs/man/rmiregistry.html) - create and start a remote object registry on the specified port on the current host
- [serialver](https://docs.oracle.com/en/java/javase/21/docs/specs/man/serialver.html) - return the `serialVersionUID` for one or more classes in a form suitable for copying into an evolving class

### Windows Only
- [jabswitch](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jabswitch.html) - enable or disable Java Access Bridge
- [jaccessinspector](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jaccessinspector.html) - examine accessible information about the objects in the Java Virtual Machine using the Java Accessibility Utilities API
- [jaccesswalker](https://docs.oracle.com/en/java/javase/21/docs/specs/man/jaccesswalker.html) - navigate through the component trees in a particular Java Virtual Machine and present the hierarchy in a tree view
- [javaw](https://docs.oracle.com/en/java/javase/21/docs/specs/man/java.html#javaw) - launch a Java application without a console window
- [kinit](https://docs.oracle.com/en/java/javase/21/docs/specs/man/kinit.html) - obtain and cache Kerberos ticket-granting tickets
- [klist](https://docs.oracle.com/en/java/javase/21/docs/specs/man/klist.html) - display the entries in the local credentials cache and key table
- [ktab](https://docs.oracle.com/en/java/javase/21/docs/specs/man/ktab.html) - manage the principal names and service keys stored in a local key table