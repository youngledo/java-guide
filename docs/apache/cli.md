# [Commons CLI](https://commons.apache.org/proper/commons-cli)

Apache Commons CLI 库提供了一个 API，用于解析传递给程序的命令行选项。它还能够打印帮助消息，详细说明命令行工具可用的选项。

Commons CLI支持不同类型的选项：
- 类似POSIX的选项，例如`tar -zxvf foo.tar.gz`
- 类似GNU的长选项，例如`du --human-readable --max-depth=1`
- 类似Java的属性，例如`java -Djava.awt.headless=true -Djava.net.useSystemProxies=true Foo`
- 附加了值的空头选项，例如`gcc -O2 foo.c`
- 带有单连字符的长选项，例如`ant -projecthelp`

Commons CLI显示的典型帮助消息如下所示：
```bash
usage: ls
 -A,--almost-all          do not list implied . and ..
 -a,--all                 do not hide entries starting with .
 -B,--ignore-backups      do not list implied entried ending with ~
 -b,--escape              print octal escapes for nongraphic characters
    --block-size <SIZE>   use SIZE-byte blocks
 -c                       with -lt: sort by, and show, ctime (time of last
                          modification of file status information) with
                          -l:show ctime and sort by name otherwise: sort
                          by ctime
 -C                       list entries by columns
```
有关详细演示，请查看[介绍](https://commons.apache.org/proper/commons-cli/introduction.html)页面。

## 文档
除了各种项目报告外，还提供了完整的用户指南。
Javadoc API文档可在线获取：
- Javadoc latest
- [Javadoc archives](https://javadoc.io/doc/commons-cli/commons-cli/latest/index.html)

可以[浏览](https://commons.apache.org/proper/commons-cli/scm.html)源存储库。

## 发布
[下载](https://commons.apache.org/proper/commons-cli/download_cli.cgi)最新版本。
还提供了发行说明。

有关早期版本，请参阅Apache[归档](https://archive.apache.org/dist/commons/cli/)。

## CLI 2？
Commons CLI 1.0是由三个不同库（Werken、Avalon和Optz）的思想和代码合并而成的。在处理bug和功能请求时，2004年创建了一个新设计的、不向后兼容的CLI2，但从未完成或发布。

目前的计划是继续维持1.x线。CLI2的作品可以在Commons Sandbox中找到。