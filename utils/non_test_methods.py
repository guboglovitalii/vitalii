import csv, random
from itertools import cycle
from datetime import datetime, timedelta
from urllib.parse import quote_plus

def open_csv_file(filepath=str):
    with open(filepath, "r") as file:
        reader = csv.DictReader(file)
        return cycle(list(reader))

def open_random_csv_file(filepath=str):
    with open(filepath, "r") as file:
        reader = csv.DictReader(file)
        return list(reader)

def generateFlightDates():
    dates_list = {}
    dates_list["depart_date"] = quote_plus((datetime.now() + timedelta(days=random.randint(2,10))).strftime("%m/%d/%Y"))
    dates_list["return_date"] = quote_plus((datetime.now() + timedelta(days=random.randint(12,30))).strftime("%m/%d/%Y"))

    return dates_list

def processCancelRequestBody(flight_ids, flight_nums):

    random_index = random.randrange(len(flight_nums))

    delete_num = f'{flight_nums[random_index]}=on'
    flight_ids = 'flightID=' + '&flightID='.join(flight_ids)
    flight_nums = '.cgifields=' + '&.cgifields='.join(flight_nums)
    static = 'removeFlights.x=57&removeFlights.y=12'

    return f'{flight_ids}&{delete_num}&{static}&{flight_nums}'
