# 학과 게시판 새 글 알림 시스템 (v 0.1)
### # 학과 게시판에 새 글이 게시되었을 때 모바일 앱으로 푸쉬 알림을 보내는 시스템입니다.
### # 시스템은 주기적으로 게시판을 파싱하여 새 글이 발견됐을 때, 푸쉬 알림을 전송합니다.
### # 사용자는 알림 받기 원하는 게시판을 추가/삭제하여 시스템으로부터 알림 기능을 제공받습니다. (Client : android device)

<br>

## 시스템 구조
<pre><code>- REST API server : https://github.com/jaeeonjin/KNUBoardAlarm_server
- Parsing Module : https://github.com/jaeeonjin/KNUBoardAlarm_deamon
- ANdroid Client : no upload
</code></pre>

<br>

## 개발환경
### # Tools
<pre><code>- IDE : STS(Spring Boot)
- DB : MySQL
</code></pre>
### # Dependencies
<pre><code>- JSoup
- MyBatis
</code></pre>

