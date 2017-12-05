[![codecov](https://codecov.io/gh/Nishant-Pathak/easy-banking/branch/master/graph/badge.svg?token=NbT8hfH5Ne)](https://codecov.io/gh/Nishant-Pathak/easy-banking)
[![Build Status](https://travis-ci.com/Nishant-Pathak/easy-banking.svg?token=q1vsdcsZMnoymmkNjFyi&branch=master)](https://travis-ci.com/Nishant-Pathak/easy-banking)
[![Jitpack](https://jitpack.io/v/Nishant-Pathak/easy-banking.svg)](https://jitpack.io/#Nishant-Pathak/easy-banking)

About
=====
Webview based OTP autoread for different banks. It uses javascript injection to parse the dom. 
It currently supports `HDFC` and `ICICI` bank.

## NOTE

We are not actively maintaining it. We worked on this a long time ago and open sourcing it so that others continue/benefit from the work.

Feel free to send a PR to add new banks. Also, if there is sufficient interest we may add support for other banks.
---------


INSTALLATION
============

To get a Git project into your build:

Add the dependency

```groovy
	dependencies {
	  compile 'com.github.bangarharshit:banklib:0.0.1'
	}
```

USAGE
=====

Add this to your activity (say MainActivity.java) which contains webview for payment flow.

```java
  private EasyBankClient easyBankClient;

  @Override protected void onCreate(Bundle savedInstanceState) {
      ...
    easyBankClient = new EasyBankBuilder()
        .addEventListener(new EventListener() {
            @Override public void onEvent(@NonNull EventCode code, @NonNull String eventName) {
              Log.d(TAG, String.format("got Event %s as: %s", code, eventName));
            }
        }).build(MainActivity.this, webView);
        ...
  }
  
  @Override protected void onDestroy() {
    easyBankClient.onDestroy();
    ...
  }

```

License
=======
    MIT License
    
    Copyright (c) 2017 Nishant
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
