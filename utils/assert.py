import random

list_ids = ['2514444-7-JB', '2514444-15-JB', '2514444-23-JB']
list_nums = ['1', '2', '3']


flight_ids = 'flightID=' + '&flightID='.join(list_ids)

numbers = '.cgifields=' + '&.cgifields='.join(list_nums)


random_index = len(list_ids)
print(random_index)


