//   [0,0,0,0,0,0,0,0,0,0....]
//下标:0 1 2 3 4 5 6 7 8 9

public class Alist{
    private int[] items;
    private int size;

    /*创建一个空的列表*/
    public AList(){
        items=new int[100];
        size=0;
    }

    /*把x这个元素插入到这个列表的末尾*/
    public void addList(int x){
        items[size++]=x;
    }

    public int getLast(){
        return items[size-1];
    }

    /*从这个列表获取元素*/
    public int get(int i){
        if(i>=items.length){
            throw new IllegalArgumentException();
        }
        return items[i];
    }

    /*返回*/
    public int size(){
        return size;
    }

    /*从列表中删除元素，返回删除的数值*/
    public int removeLast(){
        int x=items[size-1];
        items[size-1]=0;//Setting deleteditem to zero isnot necessary to
        // preserve in variants, and thus not necessary forcorrectness.
        size-=1;
        return x;
    }

}