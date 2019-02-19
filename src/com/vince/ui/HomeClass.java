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
        boolean flag = true;
        while(flag){
            println(getString("home.function"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select){
                case"1"://1、查询全部订单
                    findList();
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
                case "0"://0、退出
                    flag = false;
                    System.exit(0);
                    break;

                    default:
                        println(getString("input.error"));
            }
        }
    }
    private void findList(){}
    private void findOrderById(){}

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
        while(flag){
            println(getString("product.input.id"));
            String id = input.nextLine();
            println(getString("product.input.shoppingNum"));
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
                    break;
                case"2":
                    flag= false;
                    break;
                    default:
                        flag= false;
                        break;
            }
        }
        order.setCreatDate(DateUtils.toDate(new Date()));
        order.setUserId(currUser.getId());
        order.setSum(sum);
        order.setOrderId(orderService.list().size()+1);
        orderService.buyProduct(order);
        clothesService.update();//更新一下
        showProducts();//再显示一下

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
