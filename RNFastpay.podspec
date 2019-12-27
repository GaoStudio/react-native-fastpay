
Pod::Spec.new do |s|
  s.name         = "RNFastpay"
  s.version      = "1.0.0"
  s.summary      = "RNFastpay"
  s.description  = <<-DESC
                  RNFastpay
                   DESC
  s.homepage     = "https://github.com/GaoStudio/react-native-fastpay.git"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/GaoStudio/react-native-fastpay.git", :tag => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true
 

  s.dependency "React"
  s.static_framework = true

  #s.dependency '/ios/AlipaySDK.framework'
  s.dependency "AlipaySDK-iOS"

end

  
