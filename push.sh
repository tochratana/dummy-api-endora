message="$1"

if [ -z "$message" ]; then
    echo "Please provide a commit message."
    exit 1
fi

git add .
git commit -m "$message"
git push origin main