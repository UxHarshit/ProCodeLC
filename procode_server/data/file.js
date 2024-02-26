const fs = require('fs');
const path = require('path');

const dirPath = './data'; // Replace with the actual path to your directory

fs.readdir(dirPath, (err, files) => {
  if (err) {
    console.error('Error reading directory:', err);
    return;
  }
  var i = 0;
  var ni = 0;
  files.forEach(file => {
    const filePath = path.join(dirPath, file);
    const stats = fs.statSync(filePath);
 
    if (stats.isFile() && file.match(/\w+_alltime\.csv$/)) {
      try{
        fs.appendFileSync('file.txt', file.match(/^(\w+)(?:_alltime\.csv)$/).at(1) + '\n');
      }catch(e){
        fs.appendFileSync('file.txt', file + '\n');
      }
      i++;
    }
    
  });
  console.log(i);
    console.log(ni);
});