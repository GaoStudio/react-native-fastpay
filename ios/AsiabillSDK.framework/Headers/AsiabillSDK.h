

#import <Foundation/Foundation.h>

typedef void(^CompletionBlock)(NSString *resData);

@interface AsiabillSDK : NSObject


/**
 *  创建支付单例服务
 *
 *  @return 返回单例对象
 */
+ (AsiabillSDK *)defaultService;

/**
 *  支付接口
 *
 *  @param externalInfo       订单信息
 *  @param schemeStr      调用支付的app注册在info.plist中的scheme
 *  @param compltionBlock 支付结果回调Block
 */
- (void)payOrder:(NSString *)externalInfo
      fromScheme:(NSString *)schemeStr
        callback:(CompletionBlock)completionBlock;


@end
