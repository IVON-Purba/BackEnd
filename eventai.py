//DB에 있는 공지사항 데이터를 gpt와 연결하여 요약 후 json 반환
//openapi, mysql.connector, dotenv 모듈 설치 필요

import openai
import mysql.connector
from dotenv import load_dotenv

def analyze_and_summarize_event(event_data):
    load_dotenv()
    openai.api_key = 'chat gpt api key'

    # GPT API에 전달할 입력 구성
    prompt = f"{event_data}\n결과는 json 형식으로 반환한다. 제시하는 모든 태그를 표시해야하며, 각 태그 아래 또 다른 후속 태그를 지정하지 않고, 해당 태그에 대한 내용이 일치하는 것이 없는 경우 해당 필드의 항목은 null 값으로 대체한다. 결과에 표시해야하는 태그는 1. 텍스트 한줄 요약 event_title, 2. 날짜와 관련된 ev_startday / ev_endday, 3. 장소나 지역과 관련된 ev_location, 4. 가격이나 금액같은 금전적인 것은 ev_price에 나타내고, 5. 행사 시간과 같은 시간과 관련된 항목은 ev_time 태그로, 6. 전체적인 2~5줄 요약 내용은 ev_summary에, 7. 기타 중요한 항목은 ev_etc 항목에 나타내며, 제시된 7개의 태그를 결과 항목으로 모두 출력해야 하며 만약, 특별하게 일치하는 정보가 없다면 해당 필드의 값을 null 값으로 처리한다."
    completion = openai.Completion.create(
        engine="davinci",
        prompt=prompt,
        max_tokens=200,
        temperature=0.5,
        top_p=1.0,
        frequency_penalty=0.0,
        presence_penalty=0.0,
        stop=["###"],
        response_format={"type": "json_object"}
    )

    # AI 분석 결과 요약
    json_result = completion.choices[0].message.content

    return json_result

def load_event_data_from_database():
    # MySQL 데이터베이스 연결 설정
    connection = mysql.connector.connect(
        host="your_mysql_host",
        user="your_mysql_user",
        password="your_mysql_password",
        database="your_mysql_database"
    )
    cursor = connection.cursor()

    # 데이터베이스에서 공지사항 데이터 불러오기
    cursor.execute("SELECT * FROM event_notices WHERE event_type = '시도 행사'")

    # 공지사항 데이터 읽기
    event_data = cursor.fetchone()

    # 변경사항을 커밋하고 연결을 닫음
    connection.commit()
    cursor.close()
    connection.close()

    return event_data

if __name__ == "__main__":
    # MySQL 데이터베이스에서 공지사항 데이터 불러오기
    event_data = load_event_data_from_database()

    # AI 분석 및 요약
    summary_json = analyze_and_summarize_event(event_data)

    # JSON 형식의 결과 출력
    print(summary_json)
