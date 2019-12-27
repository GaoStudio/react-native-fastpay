#import "RNFastpay.h"
#import <AlipaySDK/AlipaySDK.h>
@implementation RNFastpay
{
    NSString *wxOpenId;
    NSString *alipayScheme;
    NSString *asiabillpayScheme;
    RCTResponseSenderBlock wxCallBack;
    RCTResponseSenderBlock alipayCallBack;
    RCTResponseSenderBlock asiabillpayCallBack;
}

RCT_EXPORT_MODULE(FastPay)

- (instancetype)init
{
    self = [super init];
    if (self) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleOpenURL:) name:@"RCTOpenURLNotification" object:nil];
    }
    return self;
}

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (BOOL)handleOpenURL:(NSNotification *)aNotification
{
    NSString * aURLString =  [aNotification userInfo][@"url"];
    NSURL * aURL = [NSURL URLWithString:aURLString];
    if ([aURL.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [[AlipaySDK defaultService] processOrderWithPaymentResult:aURL standbyCallback:^(NSDictionary *resultDic) {
            if (alipayCallBack != nil) {
                alipayCallBack([[NSArray alloc] initWithObjects:resultDic, nil]);
                alipayCallBack = nil;
            }
            NSLog(@"result = %@",resultDic);
        }];
    }
    return NO;
}


RCT_EXPORT_METHOD(setAlipayScheme:(NSString *)scheme){
    alipayScheme = scheme;
}
RCT_EXPORT_METHOD(setAsiabillPayScheme:(NSString *)scheme){
    asiabillpayScheme = scheme;
}
RCT_EXPORT_METHOD(alipay:(NSString *)info callback:(RCTResponseSenderBlock)callback)
{
    alipayCallBack = callback;
    dispatch_async(dispatch_get_main_queue(), ^{

        [[AlipaySDK defaultService] payOrder:info fromScheme:alipayScheme callback:^(NSDictionary *resultDic) {
            NSLog(@"alipay:callback");

            callback([[NSArray alloc] initWithObjects:resultDic, nil]);
        }];
    });
}



@end

