package com.yangwj.hadoop.hbase;

import com.yangwj.hadoop.utils.HBaseHelper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yangwj
 * @version 1.0
 * @date 2020/9/5 22:13
 */
public class HbaseDemo {

    static HBaseHelper helper;
    final static String tableNameString ="demoTable" ;

    public static void main(String args[]) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.33.100");
        conf.set("hbase.rootdir", "hdfs://hadoop100:9000/hbase");
        conf.set("hbase.zookeeper.property.dataDir", "/root/zookeeper/data");
        conf.set("hbase.zookeeper.property.clientPort", "2181");

        helper = HBaseHelper.getHBaseHelper(conf);

        //创建测试数据
        createDemoTable();

        //测试cas
//        CheckAndMutateExample();

        //测试get
        // getData();

        //批量处理数据
//        batchData();

        //创建testtable表数据
//        createTestTable("testtable");

        //分页过滤
//        pageFilterData();
//            跳转过滤
//        skipFilterData();
//         多个过滤器组合
//        mutilFilterData();
        //自定义过滤
        //customFilterData();

    }

    //清除并插入测试数据
    private static void createDemoTable() throws IOException {
        if (helper.existsTable(tableNameString))
            helper.dropTable(tableNameString);
        helper.createTable(tableNameString, 100, "cf1", "cf2");
        helper.put(tableNameString,
                new String[]{"row1"},
                new String[]{"cf1", "cf2"},
                new String[]{"qual1", "qual2", "qual3"},
                new long[]{1, 2, 3},
                new String[]{"val1", "val2", "val3"});
        helper.put(tableNameString,
                new String[]{"row2"},
                new String[]{"cf1", "cf2"},
                new String[]{"qual1", "qual2", "qual3"},
                new long[]{1, 2, 3},
                new String[]{"val1", "val2", "val3"});
        System.out.println("put data...");
        helper.dump(tableNameString);
    }

    //创建testtable测试数据
    private static void createTestTable(String tableNameString) throws IOException{
        if(tableNameString.isEmpty()) tableNameString = "testtable";
        if(helper.existsTable(tableNameString)){
            helper.dropTable(tableNameString);
        }
        helper.createTable(tableNameString,"info","ex","memo");

        List<Put> puts = new ArrayList<>();
        for(int i=0;i<100;i++){
            String rowKey = "rowKey"+i;
            Put put = new Put(Bytes.toBytes(rowKey));

            String columnFamily = "info";
            String columnName = "username";
            String columnValue = "user"+i;
            put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(columnName),Bytes.toBytes(columnValue));

            columnFamily = "ex";
            columnName = "addr";
            columnValue = "street"+i;
            put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(columnName),Bytes.toBytes(columnValue));

            columnFamily = "memo";
            columnName = "detail";
            columnValue = "remark"+i;
            put.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(columnName),Bytes.toBytes(columnValue));

            puts.add(put);
        }
        helper.bulkInsert2(tableNameString,puts);
    }


    //测试操作原子性compare-and-set
    private static void CheckAndMutateExample() throws IOException {
        Table table = helper.getConnection().getTable(TableName.valueOf(tableNameString));
        boolean res = false;
        Put put = null;

        put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("qual4"), 1, Bytes.toBytes("val1"));
//        如果row1 cf1 qual4 不存在值就插入put数据
        res = table.checkAndMutate(Bytes.toBytes("row1"), Bytes.toBytes("cf1"))
                .qualifier(Bytes.toBytes("qual4"))
                .ifNotExists()
                .thenPut(put);
        System.out.println("1 result is (expected true) :" + res);

//        put = new Put(Bytes.toBytes("row1"));
//        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("qual1"), 4, Bytes.toBytes("val1"));
//        //如果row1 cf1 qual1 val1存在就插入put，因为这个value已经存在所以可以插入，结果返回true，时间戳变为4
//        res = table.checkAndMutate(Bytes.toBytes("row1"), Bytes.toBytes("cf1"))
//                .qualifier(Bytes.toBytes("qual1")).ifEquals(Bytes.toBytes("val1"))
//                .thenPut(put);
//        System.out.println("2 result is (expected true) :" + res);

//        put = new Put(Bytes.toBytes("row1"));
//        put.addColumn(Bytes.toBytes("cf1"),Bytes.toBytes("qual1"),5,Bytes.toBytes("val2"));
//        ////如果row1 cf1 qual1 不等于val2在就插入put
//        res = table.checkAndMutate(Bytes.toBytes("row1"), Bytes.toBytes("cf1"))
//                .qualifier(Bytes.toBytes("qual1"))
//                .ifMatches(CompareOperator.NOT_EQUAL,Bytes.toBytes("val2"))
//                .thenPut(put);
//        System.out.println("3 result is (expected true) :" + res);

        put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("qual5"),1,Bytes.toBytes("val1"));
        put.addColumn(Bytes.toBytes("cf1"), Bytes.toBytes("qual6"),1,Bytes.toBytes("val1"));

        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumns(Bytes.toBytes("cf1"), Bytes.toBytes("qual4"));

        //RowMutations这个版本还没定型
        RowMutations mutations = new RowMutations(Bytes.toBytes("row1"));
        mutations.add(put);
        mutations.add(delete);

        //row1 cf1 qual4 val1存在,row1 cf1 qual5和row1 cf1 qual6无值则插入qual5和qual6的值，并删除qual4的值
        res = table.checkAndMutate(Bytes.toBytes("row1"),Bytes.toBytes("cf1")).qualifier(Bytes.toBytes("qual4"))
                .ifEquals(Bytes.toBytes("val1"))
                .qualifier(Bytes.toBytes("qual5")).ifNotExists()
                .qualifier(Bytes.toBytes("qual6")).ifNotExists()
                .thenMutate(mutations);
        System.out.println("1 result is (expected true) :" + res);


    }

    //get测试
    private static void getData() throws IOException{
        String key = "row1";
        String cf = "cf1";
        String cl = "qual1";

        System.out.println("first get:");
        helper.dumpCells(key,helper.getRowByKeyAndColumn(tableNameString,key,cf,cl));



        System.out.println("second get:");
        String tableName = "testtable2";
        Map<String,List<Cell>> map = helper.getRowByKeys(tableName,"rowKey7","rowKey8","rowKey9");
        for(Map.Entry<String,List<Cell>> entry:map.entrySet()){
            helper.dumpCells(entry.getKey(),entry.getValue());
        }
    }

    //批处理数据,测试数据demoTable
    //注意：同一个rowKey不能同时使用put和delete
    private static void batchData() throws IOException{
        Table table = helper.getConnection().getTable(TableName.valueOf(tableNameString));

        byte[] row1 = Bytes.toBytes("row1");
        byte[] row2 = Bytes.toBytes("row2");
        byte[] cf1 = Bytes.toBytes("cf1");
        byte[] cf2 = Bytes.toBytes("cf2");
        byte[] qualifier1 = Bytes.toBytes("qual1");
        byte[] qualifier2 = Bytes.toBytes("qual2");

        List<Row> list = new ArrayList<>();



        Put put = new Put(row1);
        put.addColumn(cf1,qualifier1,5,Bytes.toBytes("row1_batch1"));
        put.addColumn(cf2,qualifier2,5,Bytes.toBytes("row1_batch2"));
        list.add(put);

        Get get = new Get(row1);
        get.addColumn(cf1,qualifier1);
        get.addColumn(cf2,qualifier2);
        list.add(get);

        Delete delete = new Delete(row2);
        delete.addColumns(cf1,qualifier2);
        list.add(delete);

        get = new Get(row2);
        get.addFamily(Bytes.toBytes("noexists")); //列族不存在，这里将抛出异常
        list.add(get);

        Object[] results = new Object[list.size()];

        try {
            table.batch(list,results);
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0;i<results.length;i++){
            System.out.println("result["+i+"]: type = "+results[i].getClass().getSimpleName()+results[i]);
        }

        table.close();
        helper.dump(tableNameString);
        helper.close();
    }

    //分页过滤
    private static void pageFilterData() throws IOException{
        Table table = helper.getConnection().getTable(TableName.valueOf("testtable"));
        final byte[] POSTFIX = new byte[] { 0x00 };
        Filter filter = new PageFilter(10);

        int totalRows = 0;
        byte[] lastRow = null;
        while(true){
            Scan scan = new Scan();
            scan.setFilter(filter);
            if(lastRow!=null){

                //为了兼容以前的scan.setStartRow()代码
                //在上一次的最后一行加上一个空的byte数据，在下一个分页上，就会以新的key开始，
                // 但是实际上这个key并不存在，所以还是从真正的下一行开始扫描
                //这么做的原因是scan的扫描会自动包含起始行，如果不加空字节数据，那么定位上就会把上一次的最后一行作为起始行，最后的数据就会多一行。
                //而，新的api是withStartRow(byte[] startRow, boolean inclusive)，可以直接设置是否包含起始行，完美解决问题，但是又保留了对
                //以前api函数的兼容性
//                byte[] startRow = Bytes.add(lastRow,POSTFIX);
//                System.out.println("start row: " + Bytes.toStringBinary(startRow));
//                scan.withStartRow(startRow,true);


                System.out.println("start row: " + Bytes.toStringBinary(lastRow));
                //不包含起始行，所以可以直接使用上一次的最后一行作为起始行
                scan.withStartRow(lastRow,false);
            }
            ResultScanner scanner = table.getScanner(scan);
            int localRows = 0;
            Result result;
            while ((result=scanner.next())!=null){
                System.out.println(localRows++ + ": " + result);
                totalRows++;
                lastRow = result.getRow();
            }
            scanner.close();
            if(localRows==0)break;
        }
        table.close();
        System.out.println("total rows: " + totalRows);
    }


    //跳转过滤
    private static void skipFilterData() throws IOException{
        Table table = helper.getConnection().getTable(TableName.valueOf("demoTable"));
        Filter filter = new ValueFilter(CompareOperator.NOT_EQUAL,new BinaryComparator(Bytes.toBytes("val2")));

        Scan scan = new Scan();
        scan.setFilter(filter);

        ResultScanner scanner1 = table.getScanner(scan);
        System.out.println("Results of scan #1:");
        int n = 0;
        for (Result result : scanner1) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell + ", Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                n++;
            }
        }
        scanner1.close();

        //应用跳转过滤
        Filter skipFilter  = new SkipFilter(filter);
        Scan scan2 = new Scan();
        scan2.setFilter(skipFilter);
        ResultScanner scanner2 = table.getScanner(scan2);
        System.out.println("Total cell count for scan #1: " + n);
        n = 0;
        System.out.println("Results of scan #2:");
        for (Result result : scanner2) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell + ", Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                n++;
            }
        }
        scanner2.close();
        table.close();
        System.out.println("Total cell count for scan #2: " + n);

    }

    //多个过滤器
    //使用FilterList要保证过滤器的顺序需要使用List<Filter>
    private static void mutilFilterData() throws IOException{
        Table table = helper.getConnection().getTable(TableName.valueOf("testtable"));

        List<Filter> filters = new ArrayList<Filter>();

        Filter filter1 = new RowFilter(CompareOperator.GREATER_OR_EQUAL,
                new BinaryComparator(Bytes.toBytes("rowKey60")));
        filters.add(filter1);

        Filter filter2 = new RowFilter(CompareOperator.LESS_OR_EQUAL,
                new BinaryComparator(Bytes.toBytes("rowKey69")));
        filters.add(filter2);

        Filter filter3 = new QualifierFilter(CompareOperator.EQUAL,
                new RegexStringComparator("username"));
        filters.add(filter3);

        FilterList filterList1 = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);

        Scan scan = new Scan();
        scan.setFilter(filterList1);
        ResultScanner scanner1 = table.getScanner(scan);
        System.out.println("Results of scan #1 - MUST_PASS_ALL:");
        int n = 0;
        for (Result result : scanner1) {
            for (Cell cell : result.rawCells()) {
                System.out.println("Cell: " + cell + ", Value: " +
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength()));
                n++;
            }
        }
        scanner1.close();
        table.close();

    }

    //使用自定义过滤器，只显示匹配列值的行
    private static void customFilterData() throws IOException{
        Table table = helper.getConnection().getTable(TableName.valueOf("testtable"));

        List<Filter> filters = new ArrayList<Filter>();

//        Filter filter1 = new CustomFilter(Bytes.toBytes("user30"));
//        filters.add(filter1);
//
//        Filter filter2 = new CustomFilter(Bytes.toBytes("user20"));
//        filters.add(filter2);
//
//        Filter filter3 = new CustomFilter(Bytes.toBytes("user90"));
//        filters.add(filter3);

        FilterList filterList = new FilterList(
                FilterList.Operator.MUST_PASS_ONE, filters);

        Scan scan = new Scan();
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        for(Result result:scanner){
            helper.dumpResult(result);
        }

        scanner.close();
        table.close();
    }
}