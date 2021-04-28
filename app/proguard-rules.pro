# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
# 需要 LineNumberTable 属性，以消除方法内经过优化的位置之间的歧义。如需获取在虚拟机或设备上的堆栈轨迹中输出的行号，则必须使用 SourceFile 属性。
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 输出 R8 在构建项目时应用的所有规则的完整报告
-printconfiguration /tmp/full-r8-config.txt
# 生成移除代码的报告
-printusage <output-dir>/usage.txt
#  R8 根据项目的保留规则确定的入口点的报告
-printseeds <output-dir>/seeds.txt
