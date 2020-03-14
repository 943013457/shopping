package com.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapper.CategoryMapper;
import com.mapper.ProductImageMapper;
import com.mapper.ProductMapper;
import com.mapper.PropertyMapper;
import com.pojo.Category;
import com.pojo.Product;
import com.pojo.ProductImage;
import com.pojo.Property;
import com.pojo.example.CategoryExample;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Creator Ming
 * @Time 2020/3/12
 * @Other
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:Spring/Spring-*.xml")
public class pachong {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    int productCount = 0;//商品总数
    int imagCount = 0;//商品图片总数
    int propertyCount = 0;//商品参数总数
    private static ExecutorService pool;
    private static int currentThread = 0;//当前执行
    private static int maxThread = 10;


    private void pushPool(Runnable runnable) {
        try {
            currentThread++;
            pool.execute(runnable);
            while (true) {
                Thread.sleep(5000);
                System.out.println("===================当前线程数：" + currentThread);
                if (currentThread < maxThread) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("exception=>" + e.getMessage());
        }
    }

    @Test
    public void test() throws Exception {
        //生成线程池
        pool = new ThreadPoolExecutor(maxThread, 15, 30,
                TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        JSONArray jsonObject = JSONArray.parseArray("[{\"title\":\"美食\",\"value\":[\"牛奶\",\"柚子茶\",\"酸梅汤\",\"矿泉水\",\"藕粉\",\"大米\",\"小米\",\"黄豆\",\"火腿\",\"香肠\",\"木耳\",\"枸杞\",\"人参\",\"石斛\",\"雪蛤\",\"蜂蜜\",\"天麻花粉\",\"铁观音\",\"红茶\",\"花草茶\",\"龙井\",\"黑茶\",\"绿茶\",\"鸡尾酒\",\"精酿啤酒\",\"洋酒\",\"红酒\"]},{\"title\":\"生鲜\",\"value\":[\"荔枝\",\"水果\",\"百香果\",\"芒果\",\"小龙虾\",\"樱桃\",\"榴莲\",\"杨梅\",\"牛排\",\"柠檬\",\"海参\",\"水蜜桃\",\"咸鸭蛋\",\"李子\",\"桃子\",\"龙虾\",\"苹果\",\"黄桃\",\"火龙果\",\"波罗蜜\",\"山竹\",\"蓝莓\",\"鸡胸肉\",\"猕猴桃\",\"三文鱼\",\"红薯\",\"车厘子\",\"海鲜\"]},{\"title\":\"零食\",\"value\":[\"冰皮月饼\",\"零食大礼包\",\"牛肉干\",\"面包\",\"辣条\",\"红枣\",\"核桃\",\"饼干\",\"巧克力\",\"葡萄干\",\"芒果干\",\"绿豆糕\",\"薯片\",\"锅巴\",\"海苔\",\"月饼\",\"蛋黄酥\",\"猪肉脯\",\"花生\",\"长沙臭豆腐\",\"瓜子\",\"棒棒糖\",\"糖果\",\"手撕面包\",\"豆干\",\"罗汉果\",\"开心果\",\"山楂\",\"全麦面包\",\"早餐\",\"腰果\",\"压缩饼干\"]},{\"title\":\"家具\",\"value\":[\"沙发\",\"床\",\"高低床\",\"餐桌\",\"床垫\",\"茶几\",\"电视柜\",\"衣柜\",\"鞋柜\",\"椅凳\",\"书桌\",\"电脑桌\",\"坐具\",\"现代简约\",\"美式家具\",\"北欧家具\",\"中式家具\",\"儿童家具\",\"真皮沙发\",\"布艺沙发\",\"皮床\",\"实木床\",\"儿童床\",\"乳胶床垫\",\"儿童学习桌\",\"书架\",\"花架\",\"椅子\",\"电脑椅\",\"佛山家具\"]},{\"title\":\"布艺软饰\",\"value\":[\"窗帘\",\"地毯\",\"沙发垫\",\"十字绣\",\"桌布\",\"地垫\",\"抱枕\",\"坐垫\",\"飘窗垫\",\"门帘\",\"缝纫机\",\"洗衣机罩\",\"卷帘\",\"珠帘\",\"沙发巾\",\"靠垫\",\"空调罩\",\"餐桌布\",\"门垫\",\"浴室防滑垫\",\"茶几桌布\",\"桌垫\",\"装饰画\",\"摆件\",\"照片墙\",\"相框\",\"墙贴\",\"花瓶\",\"壁纸\",\"挂钟\",\"仿真花\",\"油画\",\"客厅装饰画\",\"玻璃贴纸\",\"香炉\",\"玻璃花瓶\",\"相框挂墙\",\"数字油画\",\"假花\",\"画框\",\"干花\",\"挂画\",\"钟\",\"音乐盒\",\"倒流香炉\"]},{\"title\":\"床上用品\",\"value\":[\"夏凉被\",\"草席\",\"床褥\",\"U型枕\",\"蚊帐\",\"凉席\",\"天丝套件\",\"贡缎套件\",\"提花套件\",\"婚庆套件\",\"儿童套件\",\"空调被\",\"儿童床品\",\"麻将凉席\",\"四件套\",\"毛巾被\",\"记忆枕\",\"老粗布\",\"床垫\",\"婚庆床品\",\"床笠\",\"蒙古包蚊帐\",\"空调毯\",\"枕头\",\"宫廷蚊帐\",\"牛皮席\",\"冰丝席\",\"竹席\",\"藤席\",\"床单\",\"四件套\",\"全棉套件\",\"磨毛四件套\",\"保暖套件\",\"婚庆\",\"婚庆套件\",\"儿童套件\",\"儿童床品\",\"贡缎套件\",\"提花套件\",\"被子\",\"蚕丝被\",\"羽绒被\",\"厚被\",\"冬被\",\"七孔被\",\"保暖被\",\"枕头\",\"乳胶枕\",\"羽绒枕\",\"记忆枕\",\"床褥\",\"床单被罩\",\"毛毯\",\"毯子\",\"绒毯\"]},{\"title\":\"游戏\",\"value\":[\"DNF\",\"梦幻西游\",\"魔兽\",\"LOL\",\"坦克世界\",\"剑网3\",\"魔域\",\"DOTA2\",\"街头篮球\",\"CF\",\"天龙八部\",\"大话西游2\",\"三国争霸\",\"YY\",\"劲舞团\",\"倩女幽魂\",\"天下3\",\"反恐精英\",\"冒险岛\",\"问道\",\"逆战\",\"大唐无双\",\"征途2\",\"九阴真经\",\"龙之谷\",\"热血江湖\",\"剑灵\"]},{\"title\":\"动漫周边\",\"value\":[\"手办\",\"盲盒\",\"航海王\",\"命运之夜\",\"高达模型\",\"火影忍者\",\"LOLITA洋装\",\"潮玩\",\"变形金刚\",\"钢铁侠\",\"COSPLAY服装\",\"魔道祖师\",\"BJD娃娃\",\"假面骑士\",\"名侦探柯南\",\"我的英雄学院\",\"哈利波特\",\"秦时明月\",\"剑网三\",\"游戏王\",\"兵人\",\"车模\",\"精灵宝可梦\",\"七龙珠\",\"美国队长\",\"夏目友人帐\",\"布袋戏\"]},{\"title\":\"热门影视周边\",\"value\":[\"你的名字\",\"刀剑神域\",\"动物世界\",\"大圣归来\",\"大鱼海棠\",\"毒液\",\"战狼\",\"星球大战\",\"海绵宝宝历险记\",\"深夜食堂\",\"碟中谍\",\"神奇动物\",\"老九门\",\"赛尔号\",\"黑豹\",\"玩具总动员\",\"正义联盟\",\"环太平洋\",\"金刚狼\",\"小猪佩奇\",\"流浪地球\"]},{\"title\":\"面料集采\",\"value\":[\"色卡专拍\",\"T恤汗布\",\"螺纹针织面料\",\"全棉竹节卫衣\",\"棉双纱平纹\",\"棉单面莱卡\",\"弹力莱卡\",\"色丁\",\"cvc牛津纺\",\"75d雪纺汉服面料\",\"仿真丝睡衣面料\",\"服装吊牌定做logo\",\"点点蕾丝网纱\",\"辅料里布\",\"辅料缝衣线\",\"辅料易\"]},{\"title\":\"装修\",\"value\":[\"全包\",\"半包\",\"免费设计\",\"全套设计\",\"优质装修公司\",\"样板\",\"小户型\",\"美式风\",\"宜家风\",\"集成吊顶\"]},{\"title\":\"建材\",\"value\":[\"建材优品\",\"智能马桶\",\"客厅灯\",\"LED灯泡\",\"实木地板\",\"强化地板\",\"仿古砖\",\"花砖\",\"马赛克\",\"玻化砖\",\"浴室柜\",\"花洒\",\"浴室柜套装\",\"水槽\",\"台上盆\",\"毛巾杆\",\"普通马桶\",\"龙头\",\"浴缸\",\"墙纸\",\"壁纸\",\"墙布\",\"背景墙\",\"指纹锁\",\"防盗锁\",\"监控摄像头\",\"开关插座\",\"无线摄像头\",\"门\",\"榻榻米\",\"整体橱柜\",\"楼梯\",\"定制淋浴房\",\"定制背景墙\",\"浴霸\",\"凉霸\",\"集成吊顶\"]},{\"title\":\"品质汽车\",\"value\":[\"买车送油卡\",\"v60一口价39.99万\",\"首付一成开新车\",\"超级试驾\",\"新能源车\",\"轿车\",\"SUV\",\"小型车\",\"玛莎拉蒂\",\"沃尔沃\",\"荣威\",\"别克\",\"雪佛兰\",\"日产\",\"本田\",\"起亚\",\"标致\",\"奇瑞\",\"海马\",\"宝马新1系\",\"smart\",\"Mini\",\"荣威\",\"本田\",\"天猫养车\",\"4S保养\",\"上门保养\",\"镀晶服务\",\"打蜡服务\",\"空调清洗\"]},{\"title\":\"二手车\",\"value\":[\"司法车拍卖\",\"公车拍卖\",\"二手车卖场\",\"二手车拍卖\",\"汽车估价\",\"车秒拍\",\"大众\",\"宝马\",\"奥迪\",\"丰田\",\"奔驰\",\"本田\",\"别克\",\"福特\",\"马自达\",\"雪佛兰\",\"3万以下\",\"3-5万\",\"5-10万\",\"10-20万\",\"20-30万\",\"30-40万\",\"40万以上\",\"SUV\",\"MPV\",\"跑车\",\"越野车\",\"玛莎拉蒂特价车\"]},{\"title\":\"汽车用品\",\"value\":[\"车载空气净化器\",\"脚垫\",\"夏季坐垫\",\"后备箱垫\",\"座套\",\"安全座椅\",\"香水\",\"记录仪\",\"手机支架\",\"车载导航\",\"安全预警仪\",\"后视镜导航\",\"机油\",\"燃油宝\",\"轮胎\",\"贴膜\",\"车载吸尘器\",\"镀晶\",\"车蜡\",\"洗车机\",\"轮胎报警器\",\"车充\",\"氙气灯\",\"雨刮\",\"空调滤芯\",\"大灯总成\",\"车挂\",\"安全锤\",\"应急工具\"]},{\"title\":\"鲜花园艺\",\"value\":[\"鲜花速递\",\"多肉植物\",\"干花\",\"永生花\",\"食虫植物\",\"桌面盆栽\",\"鲜果蓝\",\"仿真植物\",\"仿真蔬果\",\"开业花篮\",\"花瓶\",\"绿植同城\",\"蔬菜种子\",\"水培花卉\",\"苔藓景观\",\"空气凤梨\",\"肥料\",\"花盆花器\",\"花卉药剂\",\"营养土\",\"园艺工具\",\"洒水壶\",\"花架\",\"铺面石\",\"花卉药剂\",\"月季\",\"铁线莲\",\"绣球\"]},{\"title\":\"宠物水族\",\"value\":[\"进口狗粮\",\"宠物服饰\",\"狗厕所\",\"宠物窝\",\"航空箱\",\"海藻粉\",\"羊奶粉\",\"宠物笼\",\"狗零食\",\"剃毛器\",\"营养膏\",\"上门服务\",\"猫砂\",\"猫粮\",\"猫爬架\",\"猫砂盆\",\"化毛膏\",\"猫罐头\",\"喂食器\",\"猫抓板\",\"猫玩具\",\"猫笼\",\"水草\",\"水草泥\",\"仿真水草\",\"氧气泵\",\"过滤器\",\"鱼缸\",\"水草灯\",\"鱼粮\",\"水质维护\",\"硝化细菌\",\"除藻剂\"]},{\"title\":\"农资\",\"value\":[\"农药\",\"除草剂\",\"杀虫剂\",\"杀菌剂\",\"肥料\",\"叶面肥\",\"有机肥\",\"新型肥料\",\"氮肥\",\"磷肥\",\"钾肥\",\"种子/种苗\",\"粮油种\",\"蔬菜种\",\"果树苗\",\"食用菌菌种\",\"动物种苗\",\"饲料\",\"预混料\",\"浓缩料\",\"饲料添加剂\",\"全价料\",\"农具\",\"农膜\",\"农机\",\"农配件\",\"畜牧药品/兽药\",\"化学药\",\"中兽药\",\"消毒剂\",\"驱虫药\",\"畜牧设备\"]},{\"title\":\"鞋靴\",\"value\":[\"流行女鞋\",\"春上新\",\"当季热销\",\"潮流新品\",\"单鞋\",\"靴子\",\"运动风\",\"高跟鞋\",\"红人同款\",\"厚底鞋\",\"内增高\",\"玛丽珍鞋\",\"蝴蝶结鞋\",\"小白鞋\",\"一脚蹬\",\"圆头鞋\",\"方根鞋\",\"水晶鞋\",\"尖头鞋\",\"平底低跟\",\"穆勒鞋\",\"异型跟\",\"新年红\",\"男鞋\",\"休闲鞋\",\"板鞋\",\"帆布鞋\",\"运动风\",\"豆豆鞋\",\"乐福鞋\",\"雕花布洛克\",\"船鞋\",\"增高鞋\",\"正装商务\",\"户外休闲\",\"爸爸鞋\",\"德比鞋\",\"孟克鞋\",\"布鞋\"]},{\"title\":\"箱包\",\"value\":[\"女包\",\"骚包\",\"双肩包\",\"男包\",\"旅行箱\",\"钱包\",\"真皮包\",\"大牌\",\"宽肩带\",\"小方包\",\"水桶包\",\"迷你包\",\"链条包\",\"贝壳包\",\"波士顿包\",\"手拿包\",\"单肩包\",\"手提包\",\"斜挎包\",\"零钱包\",\"妈妈包\",\"欧美潮搭\",\"日韩流行\",\"青春学院\",\"男士商务\",\"雅痞休闲\",\"拉杆箱\",\"腰包\",\"胸包\",\"手工皮具\",\"红人优品\"]},{\"title\":\"配件配饰\",\"value\":[\"帽子\",\"贝雷帽\",\"渔夫帽\",\"鸭舌帽\",\"礼帽\",\"草帽\",\"爵士帽\",\"盆帽\",\"八角帽\",\"丝巾\",\"披肩\",\"真丝围巾\",\"棉麻围巾\",\"方巾\",\"手套\",\"真皮手套\",\"触屏手套\",\"半指手套\",\"全指手套\",\"真皮腰带\",\"腰带\",\"手工皮带\"]},{\"title\":\"运动\",\"value\":[\"Yeezy350\",\"AlphaBounce\",\"AJ30\",\"Stan Smith\",\"大Air皮蓬\",\"KD9\",\"Kayano23\",\"SockDart\",\"Hyperdunk\",\"耐克\",\"阿迪达斯\",\"NewBalance\",\"亚瑟士\",\"UnderArmour\",\"匡威\",\"彪马\",\"VANS\",\"锐步\",\"斯凯奇\",\"美津浓\",\"李宁\",\"跑鞋\",\"篮球鞋\",\"复古休闲\",\"健身\",\"足球\",\"羽毛球\"]},{\"title\":\"户外健身\",\"value\":[\"鱼线\",\"鱼线轮\",\"户外鞋\",\"登山包\",\"帐篷\",\"睡袋\",\"望远镜\",\"皮肤衣\",\"速干衣\",\"速干裤\",\"手电筒\",\"山地车\",\"公路车\",\"骑行服\",\"护具\",\"军迷用品\",\"舞蹈体操\",\"羽毛球\",\"游泳\",\"瑜伽\",\"跑步机\",\"健身器\",\"烧烤架\",\"休闲鞋\",\"冲锋裤\",\"单车零件\",\"骑行装备\",\"遮阳棚\",\"户外手表\",\"户外风衣\",\"军迷套装\",\"战术鞋\"]},{\"title\":\"乐器\",\"value\":[\"全新钢琴\",\"二手钢琴\",\"电钢琴\",\"电子琴\",\"萨克斯\",\"尤克里里\",\"架子鼓\",\"小提琴\",\"口琴\",\"手卷钢琴\",\"古筝\",\"古琴\",\"二胡\",\"葫芦丝\",\"陶笛\",\"琵琶\",\"笛子\",\"非洲鼓\",\"贝斯\",\"调音器\",\"节拍器\",\"电吉他\",\"电箱吉他\",\"乐器音箱\",\"电子鼓\",\"手风琴\",\"大提琴\",\"合成器\",\"乐器租赁\"]},{\"title\":\"珠宝\",\"value\":[\"琥珀蜜蜡\",\"翡翠手镯\",\"钻戒\",\"铂金\",\"黄金首饰\",\"高端定制\",\"彩色宝石\",\"珍珠\",\"金镶玉\",\"钻石\",\"K金首饰\",\"岫岩玉雕\",\"和田籽料拍卖\",\"裸石\",\"翡翠玉石\",\"一元起拍\",\"设计师\",\"珠宝首饰\",\"金条\",\"情侣对戒\",\"琥珀原石\",\"老坑冰种拍卖\"]},{\"title\":\"眼镜\",\"value\":[\"眼镜架\",\"3D眼镜\",\"司机镜\",\"防辐射眼镜\",\"老花镜\",\"儿童镜\",\"色盲眼镜\",\"无框眼镜\",\"眼镜片\",\"依视路\",\"雷朋\",\"复古眼镜\",\"超轻眼镜\",\"护目镜\",\"眼镜配件\",\"滑雪镜\",\"超耐磨\",\"GM眼镜\",\"配镜服务\"]},{\"title\":\"手表\",\"value\":[\"运动表\",\"卡西欧\",\"国表\",\"时尚表\",\"女表\",\"儿童表\",\"学生表\",\"浪琴\",\"斯沃琪表\",\"镂空机械表\",\"皮带表\",\"钢带表\",\"欧米茄\",\"电子表\",\"陶瓷表\",\"瑞士表\",\"手表放心淘\",\"日韩腕表\",\"情侣表\",\"光能表\",\"怀表\",\"表带\",\"手表配件\",\"休闲\",\"精钢\",\"复古手表\",\"中性手表\",\"帆布表带\",\"深度防水\"]},{\"title\":\"家电\",\"value\":[\"淘宝速达\",\"实体商场服务\",\"淘火炬品牌\",\"生活电器\",\"厨房电器\",\"个人护理\",\"空气净化器\",\"扫地机器人\",\"吸尘器\",\"取暖器\",\"烤箱\",\"豆浆机\",\"榨汁料理\",\"电饭煲\",\"吹风机\",\"足浴盆\",\"剃须刀\",\"卷发器\",\"按摩器材\",\"冬季火锅\",\"蓝牙耳机\",\"电暖桌\",\"蓝牙音箱\",\"电热毯\",\"加湿器\",\"暖风机\"]},{\"title\":\"数码\",\"value\":[\"游戏主机\",\"数码精选\",\"手机壳套\",\"苹果手机壳\",\"surface平板电脑\",\"苹果/Apple iPad Pro\",\"电脑主机\",\"数码相机\",\"电玩动漫\",\"单反相机\",\"华为MateBook\",\"IPAD mini4\",\"游戏主机\",\"鼠标键盘\",\"无人机\",\"二手数码\",\"二手手机\",\"二手笔记本\",\"二手平板电脑\"]},{\"title\":\"手机\",\"value\":[\"iPhonexs\",\"iPhonexs max\",\"iPhonexr\",\"华为Mate20P\",\"小米MIX3\",\"荣耀Magic2\",\"一加6T\",\"黑鲨2代\",\"努比亚X\",\"iPhoneX\",\"iPhone8\",\"OPPO\",\"vivo\",\"华为P20\",\"小米\",\"魅族\",\"二手手机\",\"手机以旧换新\"]},{\"title\":\"女装\",\"value\":[\"羽绒服\",\"棉衣棉服\",\"毛呢大衣\",\"毛衣针织\",\"卫衣绒衫\",\"皮衣皮草\",\"裤裙\",\"衬衫\",\"牛仔裤\",\"西装\",\"T恤\",\"大码女装\",\"时尚套装\",\"汉服\",\"半身裙\",\"风衣\",\"休闲裤\",\"蕾丝衫/雪纺衫\",\"背心吊带\",\"马夹\",\"牛仔外套\",\"阔腿裤\",\"中老年女装\",\"婚纱礼服\",\"民族服装\",\"打底裤\",\"西装裤\",\"唐装\",\"旗袍\"]},{\"title\":\"男装\",\"value\":[\"春夏新品\",\"T恤\",\"衬衫\",\"POLO衫\",\"休闲裤\",\"牛仔裤\",\"套装\",\"外套\",\"夹克\",\"卫衣\",\"风衣\",\"西装\",\"牛仔外套\",\"棒球服\",\"品质好物\",\"皮衣\",\"针织衫/毛衣\",\"运动裤\",\"工装裤\",\"开衫\",\"马甲\",\"毛呢大衣\",\"羽绒服\",\"棉衣\",\"中老年\",\"情侣装\",\"大码\",\"民族风\",\"专柜大牌\",\"明星网红\",\"原创设计\"]},{\"title\":\"内衣\",\"value\":[\"法式内衣\",\"无钢圈内衣\",\"内裤女\",\"文胸\",\"内裤男\",\"长袖睡衣\",\"睡裙\",\"真丝睡衣\",\"丝袜\",\"船袜\",\"情侣睡衣\",\"抹胸\",\"背心\",\"睡袍\",\"男士睡衣\",\"塑身衣\",\"内衣套装\",\"打底裤\",\"连体睡衣\",\"聚拢文胸\",\"男士袜子\",\"棉袜女\",\"卡通睡衣\",\"无痕内裤\",\"少女文胸\"]},{\"title\":\"童装玩具\",\"value\":[\"连衣裙\",\"保暖连体\",\"裤子\",\"羽绒\",\"居家睡衣\",\"针织\",\"帽子\",\"亲子装\",\"童鞋\",\"学步鞋\",\"女童运动鞋\",\"男童运动鞋\",\"毛毛虫童鞋\",\"雪地靴\",\"马丁靴\",\"长靴\",\"玩具\",\"积木\",\"毛绒玩具\",\"早教\",\"儿童自行车\",\"电动童车\",\"遥控模型\",\"户外玩具\",\"亲子玩具\",\"学习用品\",\"描红本\"]},{\"title\":\"孕产用品\",\"value\":[\"美妈大衣\",\"孕妇裤\",\"月子服\",\"哺乳文胸\",\"吸奶器\",\"防辐射\",\"孕妇内裤\",\"连衣裙\",\"待产包\",\"孕妇牛仔裤\",\"孕妇营养品\",\"防溢乳垫\",\"美德乐\",\"十月妈咪\",\"三洋\",\"Bravado\",\"新生儿\",\"婴儿床\",\"婴儿推车\",\"睡袋\",\"抱被\",\"隔尿垫\",\"学步车\",\"安抚奶嘴\",\"体温计\",\"纸尿裤\",\"花王\",\"洗衣液\",\"湿巾\"]},{\"title\":\"奶粉辅食\",\"value\":[\"爱他美\",\"羊奶粉\",\"特殊配方奶粉\",\"喜宝\",\"惠氏\",\"启赋\",\"牛栏\",\"美素佳儿\",\"贝因美\",\"雅培\",\"美赞臣\",\"可瑞康\",\"a2\",\"嘉宝\",\"美林\",\"米粉\",\"泡芙\",\"溶溶豆\",\"肉肠\",\"果肉条\",\"奶片\",\"益生菌\",\"维生素\",\"钙铁锌\",\"DHA\",\"宝宝食用油\",\"核桃油\",\"葡萄糖\",\"宝宝调料\",\"奶瓶\",\"餐具\",\"餐椅\",\"暖奶器\",\"消毒锅\",\"辅食机\"]}]");
//        JSONArray jsonObject = JSONArray.parseArray("[{\"title\":\"美食\",\"value\":[\"牛奶\",\"柚子茶\",\"酸梅汤\",\"矿泉水\",\"藕粉\",\"大米\",\"小米\",\"黄豆\",\"火腿\",\"香肠\",\"木耳\",\"枸杞\",\"人参\",\"石斛\",\"雪蛤\",\"蜂蜜\",\"天麻花粉\",\"铁观音\",\"红茶\",\"花草茶\",\"龙井\",\"黑茶\",\"绿茶\",\"鸡尾酒\",\"精酿啤酒\",\"洋酒\",\"红酒\"]}]");

        for (int j = 0; j < jsonObject.size(); j++) {
            System.out.println("总分类：" + jsonObject.size() + ",当前：" + j);
            JSONObject obj = jsonObject.getJSONObject(j);
            String title = obj.get("title").toString();
            int categoryid = 0;
            Category category = new Category();
            category.setName(title);

            CategoryExample categoryExample = new CategoryExample();
            categoryExample.or().andNameEqualTo(title);
            //添加分类,首先判断是否存在
            List<Category> list = categoryMapper.selectByExample(categoryExample);
            if (list.size() > 0) {
                categoryid = list.get(0).getId();
            } else {
                //分类
                categoryMapper.insert(category);
                categoryid = category.getId();
            }
            if (categoryid == 0) {
                continue;
            }
            System.out.println("添加[" + title + "," + categoryid + "]分类成功，开始插入商品");
            JSONArray jsonArray = obj.getJSONArray("value");
            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Runnable runnable = new ThreadRun1(categoryid, iterator.next().toString(), title);
                pushPool(runnable);
            }
        }
    }

    class ThreadRun1 implements Runnable {
        private String input;
        private int categoryid;
        private String title;

        public ThreadRun1(int categoryid, String input, String title) {
            this.categoryid = categoryid;
            this.input = input;
            this.title = title;
        }

        @Override
        public void run() {
            getProduct();
        }

        private void getProduct() {
            try {
                Random random = new Random();
                // 需要爬取商品信息的网站地址
                String url = "https://list.tmall.com/search_product.htm?q=" + input;

                // 提取HTML得到商品信息结果
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                        .post();
                System.out.println("搜索完成");
                // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
                Elements ulList = doc.select("div[id='J_ItemList']");
                Elements liList = ulList.select("div[class='product']");
                // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
                for (Element item : liList) {
                    // 商品名称
                    String name = item.select("div[class~=^productTitle],p[class~=^productTitle]").select("a").attr("title");
                    // 商品价格
                    Float price = Float.parseFloat(item.select("p[class='productPrice']").select("em").attr("title"));
                    //商品连接
                    String productUrl = item.select("div[class~=^productTitle],p[class~=^productTitle]").select("a").attr("href");

                    Date date = new Date();
                    date.setTime(date.getTime() - random.nextInt(30000) * 60 * 1000);

                    Product product = new Product();
                    product.setName(name);
                    product.setOriginalprice(price);
                    product.setPromoteprice(price);
                    product.setCategoryId(categoryid);
                    product.setCreatetime(date);
                    product.setSales(random.nextInt(1000));
                    product.setStock(random.nextInt(1000));
                    if (productMapper.insert(product) > 0) {
                        int id = product.getId();
                        if (id != 0) {
                            productCount++;
                            System.out.println("==============================");
                            System.out.println("【" + title + "】【" + input + "】插入商品成功id=" + id + ",当前已插入" + productCount);
                            getPropertyAndImage(product.getId(), productUrl);
                        }
                    }
                }
                currentThread--;
            } catch (Exception e) {
                System.out.println("多线程1异常------------" + e.getMessage());
            }
        }

        private void getPropertyAndImage(int productid, String url) {
            try {
                Document doc = Jsoup.connect("https:" + url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36")
                        .post();
                System.out.println("打开商品详情页完成");
                //商品副标题获取
                Elements subtitle = doc.select("div[id='J_DetailMeta']").select("div[class='tb-detail-hd']").select("p");
                if (subtitle.hasText()) {
                    Product product = new Product();
                    product.setId(productid);
                    product.setSubtitle(subtitle.text().trim());
                    if (productMapper.updateByPrimaryKeySelective(product) > 0) {
                        System.out.println("更新副标题【成功】");
                    } else {
                        System.out.println("更新副标题【失败】");
                    }
                }
                //获取商品参数
                Elements ulList = doc.select("ul[id='J_AttrUL']");
                Elements liList = ulList.select("li");
                // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
                List<Property> propertyList = new ArrayList<>();
                for (Element item : liList) {
                    String name = item.text();
                    if (name != null) {
                        String[] str = name.split(":|：");
                        if (str.length > 1) {
                            Property property = new Property();
                            property.setProductId(productid);
                            property.setParmKey(str[0]);
                            property.setParmValue(str[1]);
                            propertyList.add(property);
                        }
                    }
                }
                if (propertyList.size() > 0) {
                    propertyCount += propertyMapper.batchInsert(propertyList);
                    System.out.println("商品" + productid + "参数插入成功，当前已插入" + propertyCount);
                }
                //获取商品图片
                Elements imagelist = doc.select("ul[id='J_UlThumb']");
                Elements imglist = imagelist.select("li").select("a").select("img");
                List<ProductImage> productImageList = new ArrayList<>();
                for (Element item : imglist) {
                    String imgurl = item.attr("src").replace("_60x60q90.jpg", "");
                    ProductImage productImage = new ProductImage();
                    productImage.setLocation("small");
                    productImage.setImage(imgurl);
                    productImage.setProductId(productid);
                    productImageList.add(productImage);
                }
                if (productImageList.size() > 0) {
                    imagCount += productImageMapper.batchInsert(productImageList);
                    System.out.println("商品" + productid + "图片插入成功，当前已插入" + imagCount);
                }

            } catch (Exception e) {
                System.out.println("多线程2异常-" + e.getMessage());
            }
        }
    }


}