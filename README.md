# CRUD
android連接資料庫進行新增/刪除/查詢/修改

以下列出各功能的所在位置  詳細有在code中註解

1.新增
在MainActivity.java的 addEmployee()

2.查詢
在ViewAllEmployee.java的 getJSON()    (無傳入篩選字串，取得所有employee所有資料)
以及
在ViewEmployee.java的 getEmployee()    (以get傳入篩選字串，取得 id=? 的employee資料)
 
3.刪除
在ViewEmployee.java的 deleteEmployee()

4.修改
在ViewEmployee.java的 updateEmployee()


