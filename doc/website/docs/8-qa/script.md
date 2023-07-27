---
sidebar_position: 1
---

# 关于调用Js性能问题

本项目使用了 Nashorn 脚本引擎 来执行 Js 代码

执行流程如下：

```text
[ JavaScript源码 ] -> ( 语法分析器 Parser ) -> 
[ 抽象语法树（AST） ir ] -> ( 编译优化 Compiler ) -> 
[ 优化后的AST + Java Class文件（包含Java字节码） ] -> JVM加载和执行生成的字节码 -> 
[ 运行结果 ]
```

本项目采用 **预编译** 的策略 

也就是 最后的一步进行保存 `JVM加载和执行生成的字节码`

在脚本加载时进行编译整个Script文件夹下的所有脚本

编译为 JVM可执行的字节码 然后进行存储 放到内存当中

后续调用时 再次调用则无需编译 直接运行

和Java代码互相调用 无异

doc中的描述如下
```text
Extended by classes that store results of compilations. 
State might be stored in the form of Java classes, Java class files or scripting language opcodes. 
The script may be executed repeatedly without reparsing.  
Each CompiledScript is associated with a ScriptEngine -- A call to an eval method of the CompiledScript causes the execution of the script by the ScriptEngine. 
Changes in the state of the ScriptEngine caused by execution of the CompiledScript may visible during subsequent executions of scripts by the engine.
作者:
Mike Grogan
```
译文
```text
由存储编译结果的类扩展。
状态可能存储为Java类、Java类文件或脚本语言操作码形式。
脚本可以重复执行,而无需重新解析。
每个CompiledScript与一个ScriptEngine有关联 — 调用CompiledScript的eval方法会导致由ScriptEngine 执行该脚本。
CompiledScript 执行期间,对ScriptEngine 状态的改变可能在随后的ScriptEngine 执行中可见。
```
