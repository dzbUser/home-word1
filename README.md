#项目描述
一阶段任务
#项目用法
1）启动com.aiwan.ServerStart类，该类为服务器启动类
2）启动com.aiwan.client.Start类，启动客户端程序
#项目结构
##整体结构
1、client:客户端程序包
2、netty：网络通讯包
	1. Decoder:解码类
	2. Encoder：编码器
	3. Handler:处理类
	4. TaskDispatcher:任务分配类
3、publicSystem:项目通用协议类
	1. DecodeData:统一数据包接收类型
	2. CM_Map:客户端地图接收数据类
4、role:角色包
    1. entity:实体类包
		* user：用户表实体类
	2. dao:持久操作包
		* UserDao:用户表持久层操作类
	3. service:业务层
		* UserService:用户业务逻辑类，有功能登录，注册
	4. protocol:协议包，
		* CM_UserMessage:从客户获取的账号与密码
		* SM_UserMessage:返回用户信息给客户端
5、scenes:场景处理类
	1. dao:持久操作包
		* ScenesDao:用户位置改变持久层操作类
	2. service:业务层
		* ScenesService:用户位置改变业务逻辑类，有移动，地图跳转
	3. protocol:协议包，
		* CM_Move:从客户端获取位置移动信息类
		* CM_Shift:从客户端获取地图跳转信息类
		* SM_Move:返回移动信息给客户端
		* CM_Shift:返回跳转信息给客户端
	4. MapResource：地图资源类包，有两张地图CityResource，FieldResource，分别为主城、野外
6、util:工具类
	1. DecodeDataShift:DecodeData类转换工具类
	2. DeepClone:把对象转变成二进制
	3. GetBean:单例资源获取工具类
	4. MapResourceProtocol:地图协议
	5. Protocol:包头协议
	6. UserCache:用户缓存暂用