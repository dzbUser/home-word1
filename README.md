# 项目描述
一阶段任务
# 项目用法
1）启动com.aiwan.ServerStart类，该类为服务器启动类  
2）启动com.aiwan.client.Start类，启动客户端程序  
# 项目结构
## 整体结构

### client:客户端程序包  
### netty：网络通讯包  
	1. Decoder:解码类  
	2. Encoder：编码器  
	3. Handler:处理类  
	4. TaskDispatcher:任务分配类  
### publicSystem:项目通用协议类  
	1. initialzation包:存放初始化用的类
		* MapInitialization：地图资源初始化
		* ReflectionInitialization:反射类初始化
	2. protocol:公共协议类
		* CM_Map：公共地图接收类
		* DecodeData:类转换工具类
	3. service:公共业务逻辑包
		* ChannelManager：管理用户Channel
	4. annotation:自定义注解包（新添加）
		* ProtocolAnnotation：自定义注解，用于定位协议类
### user:角色包  
    1. entity:实体类包  
		* user：用户表实体类  
	2. dao:持久操作包  
		* UserDao:用户表持久层操作类  
	3. service:业务层  
		* UserService:用户业务逻辑类，有功能登录，注册  
	4. protocol:协议包  
		* CM_Login:用户登录协议包
		* CM_Logout:用户注销协议包
		* CM_Regitsted:用户注册协议包
		* SM_UserMessage:返回用户信息给客户端  
### scenes:场景处理类  
	1. dao:持久操作包  
		* ScenesDao:用户位置改变持久层操作类  
	2. service:业务层  
		* ScenesService:用户位置改变业务逻辑类，有移动，地图跳转  
	3. protocol:协议包  
		* CM_Move:从客户端获取位置移动信息类  
		* CM_Shift:从客户端获取地图跳转信息类  
		* SM_Move:返回移动信息给客户端  
		CM_Shift:返回跳转信息给客户端  
	4. MapResource：地图资源类包
		* MapResouce:存放静态地图资源
		* PositionMeaning:地图资源内数字的具体含义
### util:工具类  
	1. SMToDecodeData:DecodeData类转换工具类  
	2. ObjectToBytes:把对象转变成二进制  
	3. GetBean:单例资源获取工具类  
	4. MapResourceProtocol:地图协议  
	5. Protocol:包头协议  
	6. UserCache:用户缓存暂用  
	7. ConsequenceCode：结果集协议