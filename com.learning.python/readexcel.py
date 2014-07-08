import sys
import xlrd

def open_excel(file= 'file.xls'):
    try:
        data = xlrd.open_workbook(file)
        return data
    except Exception,e:
        print str(e)

def excel_table_byindex(file= 'file.xls',colnameindex=2,by_index=0):
    data = open_excel(file)
    table = data.sheets()[by_index]
    nrows = table.nrows #行数
    ncols = table.ncols #列数
    colnames =  table.row_values(colnameindex) #某一行数据 
    list =[]
    for rownum in range(colnameindex+1,nrows):
         row = table.row_values(rownum)
         if row:
             app = {}
             for i in range(len(colnames)):
                app[colnames[i]] = row[i] 
             list.append(app)
    return list

def main():
   tables = excel_table_byindex(r"C:\Users\eyimdai\Downloads\test.xls")
   for row in tables:
       print
       for rowname, rowvalue in row.items():
           print "%s" % (rowname.encode('utf-8')),

if __name__=="__main__":
    main()
