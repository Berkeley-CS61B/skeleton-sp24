//   [0,0,0,0,0,0,0,0,0,0....]
//下标:0 1 2 3 4 5 6 7 8 9

public class Alist<Cmx>{
    private Cmx[] items;
    private int size;

    /*创建一个空的列表*/
    public AList(){
        items=(Cmx[])new Object[8];//Java will complain and say,
        //you cannot do this。You connot have a placeholder word,
        //if you're creating a new array.
        size=0;
    }

    /*将底层的数组大小调整为目标容量*/
    private void resize(int capacity){
        Cmx[] a=(Cmx[])new Object[capacity]
        System.arraycopy(items,0,a,0,size);
        items=a;
    }

    /*把x这个元素插入到这个列表的末尾*/
    public void addList(int x){
        if(size==items.length){
            resize(size*2);
        }
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
    public Cmx removeLast(){
        Cmx returnItem=getLast();
        items[size-1]=0;//Setting deleteditem to zero isnot necessary to
        // preserve in variants, and thus not necessary forcorrectness.
        size-=1;
        return x;
    }

}