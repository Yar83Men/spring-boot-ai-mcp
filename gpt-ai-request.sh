echo "Запрос на Chat-GPT"
OPENAI_API_KEY="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"
read PROMPT
curl https://api.openai.com/v1/responses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -d '{
  "model": "gpt-5.6-luna",
  "messages": [{"role": "user", "content": "'" ${PROMPT} "'"}]
}'
if read -t 5 -p "У вас есть 5 секунд, чтобы ввести 'yes' для продолжения: " answer; then
    echo "Вы успели и ввели: $answer"
else
    echo -e "\nВремя вышло! Продолжаем со значениями по умолчанию."
fi