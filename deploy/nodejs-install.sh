#!/usr/bin/env sh

echo "!!! ATTENTION !!! Not a working version currently. Exiting."
exit

# The best way to install NodeJS is with NVM
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh | bash
source ~/.bashrc
nvm install --lts

cd ../lesson_angular || exit

npm install
npm run-script build