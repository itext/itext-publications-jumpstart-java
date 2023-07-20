# [iText: Jump-Start Tutorial](https://kb.itextpdf.com/home/it7kb/ebooks/itext-jump-start-tutorial-for-java) #

## How to build ##

To build the iText Jump-Start Tutorial, [Maven][1], 
[Ghostscript][2] and [Imagemagick][3] must be installed.

```bash
$ mvn clean install \
    -Dmaven.test.failure.ignore=false \
    -DgsExec=$(which gs) \
    -DcompareExec=$(which compare) \
    -Dmaven.javadoc.failOnError=false \
    > >(tee mvn.log) 2> >(tee mvn-error.log >&2)
```

[1]: http://maven.apache.org/
[2]: http://www.ghostscript.com/
[3]: http://www.imagemagick.org/
