package sml.hashTableDemo;

import java.util.Scanner;

/**
 * hashtable就是数组加链表
 */
public class HashTableDemo {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(5);
        while(true){
            System.out.println("add,list,find,exit");
            Scanner scanner = new Scanner(System.in);
            String key = scanner.next();
            switch (key){
                case "add":
                    System.out.println("id:");
                    int id = scanner.nextInt();
                    System.out.println("姓名:");
                    String name = scanner.next();
                    Emp emp = new Emp(id,name);
                    hashTable.add(emp);
                    continue;
                case "list":
                    hashTable.list();
                    continue;
                case "find":
                    System.out.println("请输入id");
                    int findId = scanner.nextInt();
                    hashTable.find(findId);
                    continue;
                case "exit":
                    scanner.close();
                    System.exit(1);
            }
        }

    }
}

//创建HashTable，管理多条链表
class HashTable{
    private int size;//表示多少条链表
    private EmpLinkedList[] empLinkedLists;

    public HashTable(int size) {
        this.size = size;
        empLinkedLists = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmpLinkedList();
        }
    }

    public void add(Emp emp){//添加
        int empLinkedListNo = hashFun(emp.id);
        empLinkedLists[empLinkedListNo].add(emp);
    }

    public void list(){//遍历
        for (int i = 0; i < size; i++) {
            System.out.println("数组"+(i+1)+":");
            empLinkedLists[i].list();
        }
    }

    public void find(int id){//查找
        Emp emp = empLinkedLists[hashFun(id)].findById(id);
        if(emp != null){
            System.out.println(emp.toString());
        }
    }


    public int hashFun(int id){
        return id % size;
    }
}

//表示雇员
class Emp{
    int id;
    String name;
    Emp next;//默认为空即可

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

//表示链表
class EmpLinkedList{
    private Emp head;//首指针
    private Emp last;//尾指针
    int size = 0;
    //添加操作
    public void add(Emp emp){
        if(head == null){
            head = emp;
            last = emp;
            head.next = last;
            size++;
            return;
        }
        if(head == last){
            head.next = emp;
            last = emp;
            size++;
            return;
        }
        last.next = emp;
        last = emp;
        size++;
    }

    //查找链表
    public Emp findById(int id){
        if(head == null){
            System.out.println("链表为空");
            return null;
        }
        Emp emp = head;
        for (int i = 0; i < size; i++) {
            if(emp.id == id){
                return emp;
            }
            if(emp.next == last){
                System.out.println("未找到");
                return null;
            }
            emp = emp.next;
        }
        return null;
    }

    //遍历链表
    public void list(){
        Emp emp = head;
        for (int i = 0; i < size; i++) {
            System.out.println(emp.toString());
            emp = emp.next;
        }
    }
}

