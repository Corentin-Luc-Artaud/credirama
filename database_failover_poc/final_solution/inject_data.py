import mysql.connector
from mysql.connector import Error
from faker import Faker

def create_fake_users():
    fake = Faker()
    users = []
    for _ in range(0, 10):
        name = fake.name()
        first_name = name.split(" ")[0]
        last_name = name.split(" ")[1]
        email = first_name + '.' + last_name + '@example.com'
        users.append((first_name, last_name, email))
    return users

try:
    connection = mysql.connector.connect(host='localhost',
                                         database='inventory',
                                         user='root',
                                         password='debezium')
    if connection.is_connected():
        db_Info = connection.get_server_info()
        print("Connected to MySQL Server version ", db_Info)
        cursor = connection.cursor()
        for user in create_fake_users():
            query = """ INSERT INTO customers VALUES (default, '""" + user[0] + """', '""" + user[1] + """', '""" + user[2] + '''')'''
            print(query)
            cursor.execute(query)
        connection.commit()
        #cursor.execute("select * from customers;")
        #record = cursor.fetchall()
        #print("You're connected to database: ", record)
except Error as e:
    print("Error while connecting to MySQL", e)
finally:
    if (connection.is_connected()):
        cursor.close()
        connection.close()
        print("MySQL connection is closed")