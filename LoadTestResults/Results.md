# Test Series #1

These tests were all performed with similar parameters.  The goal was to send 50k requests to the Tomcat server from each of two clients and see how Tomcat handles it.  In all of the tests but one, we setup 25 concurent users on each client, for a total of 50 concurrent users.

For all of the tests we are running Tomcat version 8.0.12.  Test 1 to 4 are running an out-of-the-box configuration, with only these two JVM options.

  - java.net.preferIPv4Stack=true
  - java.security.egd=file:/dev/./urandom
  - configure HTTPS connector to use certificate

Test 5 - 7 are running on an out-of-the-box configuration with APR.  These are the only customizations.

  - java.security.egd=file:/dev/./urandom 
  - java.library.path=$CATALINA_HOME/bin/tomcat-native-1.1.31-src/jni/native/.libs
  - Configure HTTPS connector to use APR
  - Configure HTTPS connector to use certificate

The APR library was build with the following configuration.

```
 ./configure --with-apr=/usr/bin/apr-1-config --with-java-home=/usr/lib/jvm/java-7-openjdk-amd64 --with-ssl
```

```
APR capabilities: IPv6 [true], sendfile [true], accept filters [false], random [true].
OpenSSL successfully initialized (OpenSSL 1.0.1f 6 Jan 2014)
```

The client machines were Ubuntu 14.04 with the latest kernel & library updates at the time.  The server machine was also running Ubuntu 14.04 with the latest OpenJDK 7.

The machines were DigitalOcean VM's running with 2 CPUs & 2G of RAM.  Communication between the VM's was done using DO's private networking option.


## Test #1 - Small 32KB File

This test was to download a small 32KB file.  For this test, `ab` was told not to use keep alive.  Here's the command that was run.

```
ab -n 50000 -c 25 -s 2 -g results.tsv http://10.128.104.109:8080/js/bootstrap.min.js
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   32.146 seconds
Complete requests:      50000
Failed requests:        0
Total transferred:      1604050000 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1555.40 [#/sec] (mean)
Time per request:       16.073 [ms] (mean)
Time per request:       0.643 [ms] (mean, across all concurrent requests)
Transfer rate:          48729.41 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    2   1.7      2      87
Processing:     1   14   7.7     12     121
Waiting:        1   11   7.5      9     109
Total:          2   16   7.1     14     122

Percentage of the requests served within a certain time (ms)
  50%     14
  66%     15
  75%     16
  80%     18
  90%     24
  95%     30
  98%     37
  99%     42
 100%    122 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   32.249 seconds
Complete requests:      50000
Failed requests:        1
   (Connect: 0, Receive: 0, Length: 0, Exceptions: 1)
Total transferred:      1604050000 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1550.46 [#/sec] (mean)
Time per request:       16.124 [ms] (mean)
Time per request:       0.645 [ms] (mean, across all concurrent requests)
Transfer rate:          48574.45 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    2   2.0      2      87
Processing:     1   14   8.0     12     164
Waiting:        1   11   7.8      9     163
Total:          2   16   7.5     14     165

Percentage of the requests served within a certain time (ms)
  50%     14
  66%     15
  75%     16
  80%     18
  90%     24
  95%     30
  98%     38
  99%     43
 100%    165 (longest request)
```

## Test #2 - Small 32KB file w/Keep-Alive

This test was to download a small 32KB file.  For this test, `ab` was told to use keep alive (`-k`).  Here's the command that was run.

```
ab -n 50000 -c 25 -s 2 -g results.tsv -k http://10.128.104.109:8080/js/bootstrap.min.js
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   27.690 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49512
Total transferred:      1604297560 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1805.74 [#/sec] (mean)
Time per request:       13.845 [ms] (mean)
Time per request:       0.554 [ms] (mean, across all concurrent requests)
Transfer rate:          56580.84 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.8      0      13
Processing:     1   14   8.4     12     167
Waiting:        1   12   6.6     11     149
Total:          1   14   8.5     12     167

Percentage of the requests served within a certain time (ms)
  50%     12
  66%     13
  75%     14
  80%     16
  90%     22
  95%     25
  98%     35
  99%     46
 100%    167 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   27.554 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49513
Total transferred:      1604297565 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1814.59 [#/sec] (mean)
Time per request:       13.777 [ms] (mean)
Time per request:       0.551 [ms] (mean, across all concurrent requests)
Transfer rate:          56858.40 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.8      0      13
Processing:     1   14   8.3     12     214
Waiting:        1   12   6.6     11     182
Total:          1   14   8.4     12     214

Percentage of the requests served within a certain time (ms)
  50%     12
  66%     13
  75%     14
  80%     16
  90%     21
  95%     24
  98%     35
  99%     46
 100%    214 (longest request)
```

## Test #3 - Medium 107KB File

This test was to download a medium sized 107KB file.  For this test, `ab` was told not to use keep alive.  Here's the command that was run.

```
ab -n 50000 -c 25 -s 2 -g results.tsv http://10.128.104.109:8080/css/bootstrap.min.css
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /css/bootstrap.min.css
Document Length:        109518 bytes

Concurrency Level:      25
Time taken for tests:   118.341 seconds
Complete requests:      50000
Failed requests:        0
Total transferred:      5488400000 bytes
HTML transferred:       5475900000 bytes
Requests per second:    422.51 [#/sec] (mean)
Time per request:       59.171 [ms] (mean)
Time per request:       2.367 [ms] (mean, across all concurrent requests)
Transfer rate:          45290.85 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    8  33.9      7    1845
Processing:     7   51 124.3     39    3057
Waiting:        2   16  89.4      9    2515
Total:          8   59 129.7     47    3064

Percentage of the requests served within a certain time (ms)
  50%     47
  66%     47
  75%     48
  80%     49
  90%     52
  95%     54
  98%     66
  99%    300
 100%   3064 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /css/bootstrap.min.css
Document Length:        109518 bytes

Concurrency Level:      25
Time taken for tests:   118.478 seconds
Complete requests:      50000
Failed requests:        0
Total transferred:      5488400000 bytes
HTML transferred:       5475900000 bytes
Requests per second:    422.02 [#/sec] (mean)
Time per request:       59.239 [ms] (mean)
Time per request:       2.370 [ms] (mean, across all concurrent requests)
Transfer rate:          45238.48 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    8  31.8      7    1844
Processing:    12   51 125.4     39    3040
Waiting:        3   16  93.2      9    2508
Total:         12   59 130.3     46    3044

Percentage of the requests served within a certain time (ms)
  50%     46
  66%     47
  75%     48
  80%     49
  90%     52
  95%     55
  98%     68
  99%    304
 100%   3044 (longest request)
```

## Test #4 - Big 5Mb File

This test was to download a large 5MB file.  For this test, `ab` was told not to use keep alive (`-k`).  It was also instructed to make 500 concurrent connections instead of the usual 25.  This was done because downloading the big file takes more time, even on a fast network, and it helped to stress the CPU on the server more.

Here's the command that was run.

```
ab -n 50000 -c 500 -s 2 -g results.tsv http://10.128.104.109:8080/big-file.dat
```

Here's the output from Client #1

```  
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /big-file.dat
Document Length:        5242880 bytes

Concurrency Level:      500
Time taken for tests:   4483.351 seconds
Complete requests:      50000
Failed requests:        25
   (Connect: 0, Receive: 0, Length: 25, Exceptions: 0)
Total transferred:      262087443424 bytes
HTML transferred:       262076043424 bytes
Requests per second:    11.15 [#/sec] (mean)
Time per request:       44833.511 [ms] (mean)
Time per request:       89.667 [ms] (mean, across all concurrent requests)
Transfer rate:          57087.83 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        6  123 359.7     21    5411
Processing: 10375 44534 6920.5  44794   79188
Waiting:        6   32  52.9     22    3393
Total:      10395 44657 6944.4  44907   79209

Percentage of the requests served within a certain time (ms)
  50%  44907
  66%  47480
  75%  49090
  80%  50152
  90%  52948
  95%  55505
  98%  58447
  99%  60571
 100%  79209 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /big-file.dat
Document Length:        5242880 bytes

Concurrency Level:      500
Time taken for tests:   4484.516 seconds
Complete requests:      50000
Failed requests:        31
   (Connect: 0, Receive: 0, Length: 31, Exceptions: 0)
Total transferred:      262077070472 bytes
HTML transferred:       262065670472 bytes
Requests per second:    11.15 [#/sec] (mean)
Time per request:       44845.159 [ms] (mean)
Time per request:       89.690 [ms] (mean, across all concurrent requests)
Transfer rate:          57070.74 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:       11  120 355.9     21    7036
Processing: 10138 44684 7141.5  45013   88530
Waiting:       12   36 111.2     22    9565
Total:      10158 44804 7166.5  45126   88550

Percentage of the requests served within a certain time (ms)
  50%  45126
  66%  47650
  75%  49286
  80%  50369
  90%  53245
  95%  55744
  98%  58705
  99%  60841
 100%  88550 (longest request)
```

## Test #5 - Small file over SSL

This test was to download a small sized 32KB file (same as #1), but over SSL.  For this test, Tomcat was configured to use the NIO Connector with SSL provided by the JDK.  Here's the command that was run to test.

```
ab -n 50000 -c 25 -s 2 -g results.tsv -k -f TLS1.2 https://10.128.104.109:8443/js/bootstrap.min.js
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8443
SSL/TLS Protocol:       TLSv1.2,ECDHE-RSA-AES256-SHA384,4096,256

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   105.315 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49514
Total transferred:      1604297570 bytes
HTML transferred:       1590950000 bytes
Requests per second:    474.77 [#/sec] (mean)
Time per request:       52.657 [ms] (mean)
Time per request:       2.106 [ms] (mean, across all concurrent requests)
Transfer rate:          14876.31 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   16 162.9      0    3509
Processing:     2   36  53.4     21    2087
Waiting:        0   24  38.3     12    1793
Total:          2   53 171.3     22    3523

Percentage of the requests served within a certain time (ms)
  50%     22
  66%     38
  75%     51
  80%     61
  90%     89
  95%    119
  98%    171
  99%   1210
 100%   3523 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8443
SSL/TLS Protocol:       TLSv1.2,ECDHE-RSA-AES256-SHA384,4096,256

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   104.886 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49510
Total transferred:      1604297550 bytes
HTML transferred:       1590950000 bytes
Requests per second:    476.71 [#/sec] (mean)
Time per request:       52.443 [ms] (mean)
Time per request:       2.098 [ms] (mean, across all concurrent requests)
Transfer rate:          14937.20 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   16 162.1      0    3845
Processing:     1   36  52.7     21    2095
Waiting:        0   24  39.4     12    1774
Total:          1   52 170.6     21    3875

Percentage of the requests served within a certain time (ms)
  50%     21
  66%     38
  75%     52
  80%     61
  90%     89
  95%    120
  98%    171
  99%   1041
 100%   3875 (longest request)
```

## Test #6 - Small file w/APR

This test was to download a small sized 32KB file (same as #1), but for this test Tomcat has been configured to use the APR Connector.  Here's the command that was run to test.

```
ab -n 50000 -c 25 -s 2 -g results.tsv -k http://10.128.104.109:8080/js/bootstrap.min.js
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   27.618 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49512
Total transferred:      1604297560 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1810.40 [#/sec] (mean)
Time per request:       13.809 [ms] (mean)
Time per request:       0.552 [ms] (mean, across all concurrent requests)
Transfer rate:          56727.01 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.3      0      42
Processing:     3   14   5.0     12     108
Waiting:        3   12   3.2     12     104
Total:          3   14   5.3     12     108

Percentage of the requests served within a certain time (ms)
  50%     12
  66%     13
  75%     13
  80%     13
  90%     21
  95%     23
  98%     26
  99%     33
 100%    108 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8080

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   27.447 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49510
Total transferred:      1604297550 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1821.67 [#/sec] (mean)
Time per request:       13.724 [ms] (mean)
Time per request:       0.549 [ms] (mean, across all concurrent requests)
Transfer rate:          57080.07 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.8      0      67
Processing:     3   14   4.7     12     118
Waiting:        3   12   3.0     12     101
Total:          4   14   5.1     12     118

Percentage of the requests served within a certain time (ms)
  50%     12
  66%     13
  75%     13
  80%     13
  90%     21
  95%     23
  98%     26
  99%     32
 100%    118 (longest request)
```

## Test #7 - Small file w/APR over SSL

This test was to download a small sized 32KB file (same as #1), but over SSL.  For this test, Tomcat was configured to use the APR Connector with SSL provided by OpenSSL.  Here's the command that was run to test.

```
ab -n 50000 -c 25 -s 2 -g results.tsv -k -f TLS1.2 -Z "ECDHE-RSA-AES256-SHA384" https://10.128.104.109:8443/js/bootstrap.min.js
```

Here's the output from Client #1.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8443
SSL/TLS Protocol:       TLSv1.2,ECDHE-RSA-AES256-SHA384,4096,256

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   40.943 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49510
Total transferred:      1604297550 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1221.21 [#/sec] (mean)
Time per request:       20.472 [ms] (mean)
Time per request:       0.819 [ms] (mean, across all concurrent requests)
Transfer rate:          38265.26 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    3  29.5      0     721
Processing:     1   18  20.3     11     305
Waiting:        1   10  14.3      6     292
Total:          1   20  35.8     11     759

Percentage of the requests served within a certain time (ms)
  50%     11
  66%     14
  75%     17
  80%     21
  90%     41
  95%     67
  98%    106
  99%    158
 100%    759 (longest request)
```

Here's the output from Client #2.

```
Server Software:        Apache-Coyote/1.1
Server Hostname:        10.128.104.109
Server Port:            8443
SSL/TLS Protocol:       TLSv1.2,ECDHE-RSA-AES256-SHA384,4096,256

Document Path:          /js/bootstrap.min.js
Document Length:        31819 bytes

Concurrency Level:      25
Time taken for tests:   41.676 seconds
Complete requests:      50000
Failed requests:        0
Keep-Alive requests:    49514
Total transferred:      1604297570 bytes
HTML transferred:       1590950000 bytes
Requests per second:    1199.73 [#/sec] (mean)
Time per request:       20.838 [ms] (mean)
Time per request:       0.834 [ms] (mean, across all concurrent requests)
Transfer rate:          37592.11 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    3  29.4      0     773
Processing:     1   18  20.5     11     264
Waiting:        1   10  14.1      6     262
Total:          1   21  35.9     11     813

Percentage of the requests served within a certain time (ms)
  50%     11
  66%     15
  75%     19
  80%     24
  90%     40
  95%     67
  98%    106
  99%    166
 100%    813 (longest request)
```

## Notes

  - CPU usage is initially higher when the server starts (probably due to the JIT).  After starting the server, I needed to run a few rounds of the test to stabilize things.
  - CPU usage was lower with larger files, possibly due to Tomcat's sendfile support or just due to the fact that transferring the files over the network slows things down.  In fact, while testing the big file download, I had to bump up the number of clients significantly to get any increase in CPU usage.
  - During the large file test, Tomcat was able to saturate the 1Gbps network connection at roughly 50 - 75% CPU utilization.
  - Using the APR connector showed roughly a 20% reduction in CPU over the NIO connector when serving small files.
