
# react-native-fastpay

## Getting started

`$ npm install react-native-fastpay --save`

### Mostly automatic installation

`$ react-native link react-native-fastpay`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-fastpay` and add `RNFastpay.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFastpay.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNFastpayPackage;` to the imports at the top of the file
  - Add `new RNFastpayPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-fastpay'
  	project(':react-native-fastpay').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-fastpay/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-fastpay')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNFastpay.sln` in `node_modules/react-native-fastpay/windows/RNFastpay.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Fastpay.RNFastpay;` to the usings at the top of the file
  - Add `new RNFastpayPackage()` to the `List<IReactPackage>` returned by the `Packages` method
  
### Config

#### iOS

1. TARGET -> Build Phases -> Linked Binary With Libraries 添加以下系统库
![image](https://github.com/puti94/react-native-puti-pay/blob/master/screenshot/WX20171125-142402.png)

2. 在项目中的info.plist中加入应用白名单，右键info.plist选择source code打开(plist具体设置在Build Setting -> Packaging -> Info.plist File可获取plist路径) :

   ```
   <key>LSApplicationQueriesSchemes</key>
   <array>
       <!-- 支付宝 URL Scheme 白名单-->
       <string>alipay</string>
       <!-- 微信 URL Scheme 白名单-->
       <string>wechat</string>
       <string>weixin</string>
   
   </array>
   ```

3. 设置 URL Scheme URL Scheme是通过系统找到并跳转对应app的设置，通过向项目中的info.plist文件中加入URL types可使用第三方平台所注册的appkey信息向系统注册你的app，当跳转到第三方应用支付后，可直接跳转回你的app。微信填写微信ID,支付宝也建议添加ap+加支付宝应用id的形式以免冲突。

   ![image](https://github.com/puti94/react-native-puti-pay/blob/master/screenshot/WX20171125-142504.png)

4. 在入口文件AppDelegate.m下设置回调

   ```
   
   #import <React/RCTLinkingManager.h>
   
     - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
     {
       return [RCTLinkingManager application:application openURL:url
                           sourceApplication:sourceApplication annotation:annotation];
     }
   
     - (BOOL)application:(UIApplication *)application openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey, id> *)options
     {
       return [RCTLinkingManager application:application openURL:url options:options];
     }
   ```


## Usage
```javascript
import RNFastpay from 'react-native-fastpay';

// TODO: What to do with the module?
RNFastpay;
```
  
