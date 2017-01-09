# Loader2
<pre name="code" class="html">Loader2是Loader的细分版本，将网络加载分成页面交互，加载和缓存3个部分。
lib-loader-base负责与页面交互
lib-loader-retrofit是用Retrofit进行加载数据，如果项目不是用Retrofit，则不要调用该模块，并实现相应的IModel。
lib-loader-rxcache是用RxJava2进行缓存读写,如果不想用RxJava2，则不要调用该模块，并实现相应的ICacheHelper。</pre>
<br />
#使用方法
使用方法与Loader的使用方法大体一致，但若要实现缓存，则需要在Model中setCacheHelper
