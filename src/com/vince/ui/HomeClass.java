package com.vince.ui;

import com.vince.bean.Clothes;
import com.vince.bean.Order;
import com.vince.bean.OrderItem;
import com.vince.service.ClothesService;
import com.vince.service.Impl.ClothesServiceImpl;
import com.vince.service.Impl.OrderServiceImpl;
import com.vince.service.OrderService;
import com.vince.utils.BusinessException;
import com.vince.utils.ConsoleTable;
import com.vince.utils.DateUtils;
import com.vince.utils.ProductsXmlUtils;

import java.awt.*;
import java.util.Date;
import java.util.List;

public class HomeClass extends BaseClass{

    //导包 OrderService已创建好，创建对象
    private OrderService orderService = new OrderServiceImpl();
    private ClothesService clothesService = new ClothesServiceImpl();

    public void show(){
        showProducts();
        println("welcome:"+currUser.getUsername());
        menu();
    }
    public void menu(){
        boolean flag = true;
        while(flag){
            println(getString("home.function"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select){
                case"1"://1、查询全部订单
                    findOrderList();
                    flag = false;
                    break;
                case "2"://2、查询订单
                    findOrderById();
                    flag = false;
                    break;
                case"3"://3、购买
                    try {
                        buyProducts();
                        flag = false;
                    } catch (BusinessException e) {
                        println(e.getMessage());
                    }
                    break;
                case"4"://显示商品
                    show();
                    break;
                case "0"://0、退出
                    flag = false;
                    println(getString("info.exit"));
                    System.exit(0);
                    break;

                    default:
                        println(getString("input.error"));
                        break;
            }
        }
    }

    /**
     * 购买商品
     * @throws BusinessException
     */
    private void  buyProducts() throws BusinessException {
        //生成订单
        //提醒用户输入信息，生成订单
        boolean flag = true;
        int count = 1;
        float sum = 0.0f;//订单总金额
        Order order = new Order();//生成的订单
        List<Clothes> list = ProductsXmlUtils.perserProductFormXml();//商品列表
        while(flag){
            println(getString("product.input.id"));
            String id = input.nextLine();
            for(Clothes c :list) {
                if (!id.equals(c.getId())) {
                    println(getString("input.error"));

                } else {
                    println(getString("product.input.shoppingNum"));
                }break;
            }
            String shoppingNum = input.nextLine();
            OrderItem orderItem = new OrderItem();
            Clothes clothes =clothesService.findById(id);

            int num = Integer.parseInt(shoppingNum);
            if(num>clothes.getNum()){
                throw new BusinessException("product.num.error");
            }
            //一条订单明细
            clothes.setNum(clothes.getNum()-num);//减去库存
            orderItem.setClothes(clothes);
            orderItem.setShoppingNum(num);
            orderItem.setSum(clothes.getPrice()*num);
            sum+=orderItem.getSum();
            orderItem.setItemId(count++);
            order.getOrderItemList().add(orderItem);

            println(getString("product.buy.continue"));
            String isBuy = input.nextLine();
            switch (isBuy){
                case"1":
                    flag = true;
                    buyProducts();
                    break;
                case"2":
                    flag= false;
                    show();
                    break;
                    default:
                        flag= false;
                        println(getString("input.error"));
                        break;
            }
        }
        order.setCreatDate(DateUtils.toDate(new Date()));
        order.setUserId(currUser.getId());
        order.setSum(sum);
        order.setOrderId(orderService.list().size()+1);
        orderService.buyProduct(order);
        clothesService.update();//更新一下
        show();//再显示一下

    }
    private void findOrderById(){//2、查询订单
        println(getString("product.order.input.oid"));
        String oid = input.nextLine();
        Order order = orderService.findById(Integer.parseInt(oid));
        if(oid.equals(order.getOrderId())){
            showOrder(order);//显示订单
        }else{
            println(getString("product.order.error"));
        }
        menu();//显示菜单
    }
    //查询订单列表
    private void findOrderList(){
        List<Order> list = orderService.list();
        for(Order o: list){
            showOrder(o);
        }
        menu();
    }


    private void showOrder(Order o){
        print(getString("product.order.oid")+o.getOrderId());
        print("\t"+getString("product.order.createDate")+o.getCreatDate());
        println("\t"+getString("product.order.sum")+o.getSum());
        ConsoleTable t= new ConsoleTable(9,true);
        t.appendRow();
        t.appendColum("itemid")
                .appendColum("brand")
                .appendColum("style")
                .appendColum("color")
                .appendColum("size")
                .appendColum("price")
                .appendColum("description")
                .appendColum("shoppingNum")
                .appendColum("sum");
        for(OrderItem item : o.getOrderItemList()){
            t.appendRow();
            t.appendColum(item.getItemId())
                    .appendColum(item.getClothes().getBrand())
                    .appendColum(item.getClothes().getStyle())
                    .appendColum(item.getClothes().getColor())
                    .appendColum(item.getClothes().getSize())
                    .appendColum(item.getClothes().getPrice())
                    .appendColum(item.getClothes().getDescription());
        }
        println(t.toString());

    }
    public void showProducts(){
        List<Clothes> list = clothesService.list();

        ConsoleTable t = new ConsoleTable(8, true);//有多少列，要不要打印表头
        t.appendRow();
        t.appendColum("id")
                .appendColum("brand").
                appendColum("style").
                appendColum("color").
                appendColum("size").
                appendColum("num").
                appendColum("price").
                appendColum("description");
        for(Clothes c : list){
            t.appendRow();
            t.appendColum(c.getId())
                    .appendColum(c.getBrand()).
                    appendColum(c.getStyle()).
                    appendColum(c.getColor()).
                    appendColum(c.getSize()).
                    appendColum(c.getNum()).
                    appendColum(c.getPrice()).
                    appendColum(c.getDescription());
        }
        println(t.toString());
    }
}
