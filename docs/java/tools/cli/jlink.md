您可以使用 jlink 工具将一组模块及其依赖项组装和优化到自定义运行时映像中。

### 概要

```shell
jlink [options] --module-path modulepath --add-modules module[,module...]
```

#### options

由空格分隔的命令行选项。请参见`jlink`选项。

#### modulepath

`jlink`工具发现可观察模块的路径。这些模块可以是模块化的XML文件、JMOD文件或分解模块。

#### module

要添加到运行库映像的模块的名称。`jlink`工具添加这些模块及其传递依赖项。

### 描述

`jlink`工具链接一组模块，沿着它们的可传递依赖项，以创建自定义运行时映像。
> :warning: 注意：开发人员负责更新他们的自定义运行时映像。

### jlink选项

#### --add-modules mod[,mod...]

将命名模块 mod 添加到默认的根模块集。默认的根模块集为空。

#### --bind-services

链接服务提供程序模块及其依赖项。

#### -c={0|1|2} or --compress={0|1|2} -c={0|1|2} 或 --compress={0|1|2}

启用资源压缩：

- 0 ：无压缩
- 1 ：常量字符串共享
- 2 ：ZIP

#### --disable-plugin pluginname

禁用指定的插件。有关支持的插件列表，请参见jlink插件。

#### --endian {little|big}

指定生成图像的字节顺序。默认值是系统体系结构的格式。

#### -h or --help -h 或 --help

打印帮助消息。

#### --ignore-signing-information

在运行时映像中链接签名的模块化JAR时，禁止出现致命错误。已签名的模块化JAR的签名相关文件不会复制到运行时映像中。

#### --launcher command=module or --launcher command=module/main --launcher command=module 或 --launcher command=module/main

指定模块的启动器命令名或模块和主类的命令名（模块和主类的名称由斜杠（ / ）分隔）。

#### --limit-modules mod[,mod...]

将可观察模块的范围限制为命名模块的传递闭包中的模块，`mod`，加上主模块（如果有的话），再加上在`--add-modules`选项中指定的任何其他模块。

#### --list-plugins

列出可用的插件，您可以通过命令行选项访问这些插件。请参见[jlink插件](#jlink插件)。

#### -p or --module-path modulepath -p 或 --module-path modulepath

指定模块路径。

如果未指定此选项，则默认模块路径为`$JAVA_HOME/jmods`。此目录包含`java.base`模块以及其他标准和JDK模块。如果指定了此选项，但无法从中解析
`java.base`模块，则`jlink`命令会将`$JAVA_HOME/jmods`追加到模块路径。

#### --no-header-files

不包括头文件。

#### --no-man-pages

不包括手册页。

#### --output path

指定生成的运行时映像的位置。

#### --save-opts filename

在指定的文件中删除`jlink`选项。

#### --suggest-providers [name, ...]

建议从模块路径实现给定服务类型的提供程序。

#### --version

打印版本信息。

#### @filename

从指定文件读取选项。

选项文件是一个文本文件，其中包含通常在命令提示符中输入的选项和值。选项可以显示在一行或多行上。不能为路径名指定环境变量。您可以通过在行的开头添加一个哈希符号（ #
）来注释掉行。

以下是`jlink`命令的选项文件示例：

```
#Wed Dec 07 00:40:19 EST 2016
--module-path mlib
--add-modules com.greetings
--output greetingsapp
```

### jlink插件

> :warning: 注意：本节中未列出的插件不受支持，并且可能会发生更改。

对于需要 pattern-list 的插件选项，该值是一个逗号分隔的元素列表，每个元素使用以下形式之一：

- glob-pattern
- glob:glob-pattern
- regex:regex-pattern
- @filename
    - `filename`是包含要使用的模式的文件名，每行一个模式。

要获得所有可用插件的完整列表，请运行命令`jlink --list-plugins`。

#### 表2-4 jlink插件列表

| Plugin Name 插件名称 | Option 选项                                | Description 描述                                                                                                                      |
|------------------|------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| compress         | --compress={0,1,2}[:filter=pattern-list] | 压缩输出图像中的所有资源。 <div>- 级别0：无压缩</div> <div>- 级别1：常量字符串共享</div> <div>- 级别2：ZIP</div><p>可以指定可选的pattern-list过滤器来列出要包含的文件模式。</p>           |
| include-locales  | --include-locales=langtag[,langtag]*     | 包括区域设置列表，其中langtag是BCP 47语言标记。此选项支持RFC 4647中定义的区域设置匹配。使用此选项时确保添加模块jdk.localedata。<p>例如：`--add-modules jdk.localedata --include`</p> |
| order-resources  | --order-resources=pattern-list           | 按优先级顺序排列指定路径。如果指定了`@filename`，那么`pattern-list`中的每一行都必须与要排序的路径完全匹配。<p>例如：`--order-resources=/module-info.class,@`</p>                |
| strip-debug      | --strip-debug                            | 从输出映像中剥离调试信息。                                                                                                                       |

#### jlink示例

以下命令在目录`greetingsapp`中创建运行时映像。此命令链接模块`com.greetings`，其模块定义包含在目录`mlib`中。

```shell
jlink --module-path mlib --add-modules com.greetings --output greetingsapp
```

以下命令列出运行时映像`greetingsapp`中的模块：

```shell
greetingsapp/bin/java --list-modules
com.greetings
java.base@11
java.logging@11
org.astro@1.0
```

以下命令在目录 compressedrt 中创建一个运行时映像，该映像已去除调试符号，使用压缩来减少空间，并包含法语语言区域设置信息：

```shell
jlink --add-modules jdk.localedata --strip-debug --compress=2 --include-locales=fr --output compressedrt
```

下面的示例比较了运行时映像`compressedrt`和`fr_rt`的大小，`fr_rt`没有剥离调试符号，也没有使用压缩：

```shell
jlink --add-modules jdk.localedata --include-locales=fr --output fr_rt

du -sh ./compressedrt ./fr_rt
23M     ./compressedrt
36M     ./fr_rt
```

以下示例列出了实现`java.security.Provider`的提供程序：

```shell
jlink --suggest-providers java.security.Provider

Suggested providers:
  java.naming provides java.security.Provider used by java.base
  java.security.jgss provides java.security.Provider used by java.base
  java.security.sasl provides java.security.Provider used by java.base
  java.smartcardio provides java.security.Provider used by java.base
  java.xml.crypto provides java.security.Provider used by java.base
  jdk.crypto.cryptoki provides java.security.Provider used by java.base
  jdk.crypto.ec provides java.security.Provider used by java.base
  jdk.crypto.mscapi provides java.security.Provider used by java.base
  jdk.security.jgss provides java.security.Provider used by java.base
```

下面的示例创建一个名为`mybuild`的自定义运行时映像，其中仅包括`java.naming`和`jdk.crypto.cryptoki`
及其依赖项，但不包括其他提供程序。请注意，这些依赖项必须存在于模块路径中：

```shell
jlink --add-modules java.naming,jdk.crypto.cryptoki --output mybuild
```

下面的命令与创建名为 greetingsapp 的运行时映像的命令类似，不同之处在于它将从根模块解析的模块与服务绑定链接;请参阅
`Configuration.resolveAndBind`方法。

```shell
jlink --module-path mlib --add-modules com.greetings --output greetingsapp --bind-services
```

以下命令列出了该命令创建的运行时映像`greetingsapp`中的模块：

```shell
greetingsapp/bin/java --list-modules
com.greetings
java.base@11
java.compiler@11
java.datatransfer@11
java.desktop@11
java.logging@11
java.management@11
java.management.rmi@11
java.naming@11
java.prefs@11
java.rmi@11
java.security.jgss@11
java.security.sasl@11
java.smartcardio@11
java.xml@11
java.xml.crypto@11
jdk.accessibility@11
jdk.charsets@11
jdk.compiler@11
jdk.crypto.cryptoki@11
jdk.crypto.ec@11
jdk.crypto.mscapi@11
jdk.internal.opt@11
jdk.jartool@11
jdk.javadoc@11
jdk.jdeps@11
jdk.jfr@11
jdk.jlink@11
jdk.localedata@11
jdk.management@11
jdk.management.jfr@11
jdk.naming.dns@11
jdk.naming.rmi@11
jdk.security.auth@11
jdk.security.jgss@11
jdk.zipfs@11
org.astro@1.0
```