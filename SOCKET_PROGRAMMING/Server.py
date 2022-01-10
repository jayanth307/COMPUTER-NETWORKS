import socket
import os
from _thread import *
import pandas as pd

connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
ThreadCount = 0
try:
    connection.bind((socket.gethostname(), 4751))
except socket.error as e:
    print(str(e))

print('WAITING FOR AN CONNECTION..')
connection.listen(5)

col_names = ["Name","Phone_No","District","Pincode","Area","Problem","Verification","Execution_Status"]
filename = "problem.csv"
df = pd.read_csv(filename)
df.columns = col_names

def showData(df,column_name,value):
	g = df.groupby(column_name)
	return g.get_group(value)

def getUserData(df,Phone_No):
	df = df.iloc[Phone_No]
	print(df)
	return df

def modifyData(df, Phone_No, new_value):
	for index in df.index:
 		if df.loc[index,"Phone_No"] == Phone_No :
 			df.loc[index,"Execution_Status"] = new_value
	return df

def update(df):
	df["Execution_Status"] = df["Execution_Status"].fillna("NotExecuted")
	return df

def delete(df):
	for index in df.index:
		if df.loc[index,"Verification"] == "Not Verified":
			df.drop([index],axis=0,inplace=True)
	return df

def threaded_client(connection):

	while True:
		data = connection.recv(4048).decode('utf-8')
		
		if not data:
			break
		data = str(data)
		print("From Connected User : "+data)

		if data == "V":
			show_data = df.to_string()
			connection.send(show_data.encode())
		elif data.find("M") != -1:
			split_data = data.split()
			show_data = modifyData(df,split_data[1],split_data[2]).to_string()
			connection.send(show_data.encode())
			df.to_csv("problem.csv",index=False)
		elif data.find("U") != -1:
			show_data = update(df).to_string()
			connection.send(show_data.encode())
			df.to_csv("problem.csv",index=False)
		elif data.find("F") != -1:
			show_data = delete(df).to_string()
			connection.send(show_data.encode())
			df.to_csv("Executedlist.csv",index=False)
			

        
  
	connection.close()


while True:
    clt, adr = connection.accept()
    print(f"Connection established to {adr} established")
    start_new_thread(threaded_client, (clt,))
    ThreadCount += 1
    print('Thread Number: ' + str(ThreadCount))

connection.close()
