
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif
#import "WXApi.h"
@interface RNFastpay : NSObject <RCTBridgeModule,WXApiDelegate>

@end

