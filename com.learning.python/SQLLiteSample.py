import xlrd
import sqlite3

device_city_db = sqlite3.connect('device_city.db')
cursor = device_city_db.cursor()

cursor.execute('DROP TABLE IF EXISTS device_city')
cursor.execute('CREATE TABLE device_city (device_id char(16) PRIMARY KEY, city varchar(16))')

device_id = 1
city = 'Shanghai'
cursor.execute('INSERT INTO device_city (device_id, city) VALUES (?, ?)',(device_id, city))

cursor.execute('SELECT * FROM device_city WHERE device_id=?', (device_id,))
res = cursor.fetchone()
if res == None:
    cursor.execute('INSERT INTO device_city (device_id, city) VALUES (?, ?)', (device_id, city))
else:
    print '%s, %s => %s, %s' % (device_id, city, res[0], res[1])
device_city_db.commit()
