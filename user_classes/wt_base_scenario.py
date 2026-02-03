from locust import task, SequentialTaskSet, HttpUser, constant_pacing, events
from config.config import cfg, logger


class PurchaseFlightTicket(SequentialTaskSet): # класс с задачами (содержит основной сценарий)
    @task()
    def uc_00_getHomePage(self):
        r001_01_response = self.client.get(
            '/WebTours/',
            name="r001_01_response",
            allow_redirects=False,
            headers={
                'sec-ch-ua': 'Not(A:Brand";v="8", "Chromium";v="144", "Microsoft Edge";v="144',
                'sec-ch-ua-mobile': '?0'
            } 
        )
        logger.info(f"Статус ответа: {r001_01_response.status_code}, Тело ответа: {r001_01_response.text}")
        print(f"Статус ответа: {r001_01_response.status_code}, Тело ответа: {r001_01_response.text}") 

class WebToursBaseUserClass(HttpUser): # юзер-класс, принимающий в себя основные параметры теста
    wait_time = constant_pacing(cfg.pacing)
    host = cfg.url
    tasks = [PurchaseFlightTicket]