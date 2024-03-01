document.addEventListener('DOMContentLoaded', function() {
    fetchTopics();
});

function fetchTopics() {
    fetch('http://localhost:8080/madalart/main')
        .then(response => response.json())
        .then(data => displayTopics(data))
        .catch(error => console.error('Error fetching topics:', error));
}

function displayTopics(topics) {
    const topicsContainer = document.getElementById('topics');
    topics.forEach(topic => {
        const topicDiv = document.createElement('div');
        topicDiv.classList.add('topic-item');
        topicDiv.textContent = topic.title; // 'title'은 MainTopicDto의 속성입니다.
        topicsContainer.appendChild(topicDiv);
    });
}