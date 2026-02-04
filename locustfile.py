""" from locust import HttpUser, task, between
from dotenv import load_dotenv
import os

load_dotenv()

class MyUser(HttpUser):
    host = os.getenv("TARGET_URL")  # например http://localhost:1080
    wait_time = between(1, 3)

    @task
    def index(self):
        with self.client.get("/WebTours/", catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"Ошибка при GET /WebTours/: {response.status_code}")

    @task
    def login_logout(self):
        payload = {
            "username": os.getenv("USERNAME"),
            "password": os.getenv("PASSWORD"),
            "login.x": "57",
            "login.y": "8"
        }
        with self.client.post("/cgi-bin/login.pl", data=payload, catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"Ошибка при POST /cgi-bin/login.pl: {response.status_code}")

        # просмотр меню (условно "профиль")
        self.client.get("/cgi-bin/nav.pl?page=menu&in=home")

        # выход
        self.client.get("/cgi-bin/login.pl?logout=1")
 """

from config.config import cfg, logger


if cfg.webtours_base.included:
    from user_classes.wt_base_scenario import WebToursBaseUserClass
    WebToursBaseUserClass.weight = cfg.webtours_base.weight
    
    logger.info("WebToursBaseUserClass started")



