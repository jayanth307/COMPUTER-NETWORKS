import socket

def client_program():
	print("V - VIEW")
	print("F - FILTER")
	print("U - UPDATE")
	print("M - MODIFY")
	print("exit")



	client_socket = socket.socket()
	host = socket.gethostname()
	port = 4751

	print('Waiting for connection response')

	try:
		client_socket.connect((host,port))
	except socket.error as e:
		print(str(e))

	result = input(" => ")

	while result.lower().strip()!="exit":
		client_socket.send(result.encode())
		data = client_socket.recv(4048).decode()

		print("RECEIVED FROM SERVER : \n"+ data)
						
		result = input('=>')
		
	client_socket.close()

if __name__ == "__main__":
	client_program()