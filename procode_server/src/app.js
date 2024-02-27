const express = require('express');
const csv = require('fast-csv');
const path = require('path');

const app = express();
const fs = require('fs');
const port = 3000;

app.use(express.json());

var file_data = fs.readFileSync('./public/company.txt', 'utf8');
var data = file_data.split('\n');


app.get('/getCompany', (req, res) => {

    res.status(200).json(data);
});


function findCsvFile(directoryPath, fileName) {
    const files = fs.readdirSync(directoryPath);
    for (const file of files) {
        const filePath = path.join(directoryPath, file);
        if (path.extname(filePath) === '.csv' && file === fileName) {
            return filePath;
        }
    }
    return null;
}

var readCsvFile = async (filePath) => {
    return new Promise((resolve, reject) => {
        var results = [];
        fs.createReadStream(filePath)
            .pipe(csv.parse({ headers: true }))
            .on('error', error => {
                console.error(error);
                reject(error);
            })
            .on('data', row => {
                results.push(row);
            })
            .on('end', rowCount => {
                if (results.length > 0) {
                    resolve(results);
                } else {
                    console.warn('CSV file is empty');
                    reject('CSV file is empty');
                }
            });
    });
}


app.post('/getCompanyInfo', async (req, res) => {
    console.log(req.body);
    const { company } = req.body;
    const directoryPath = './data';
    const fileName = company + '_alltime.csv';
    const filePath = findCsvFile(directoryPath, fileName);
    if (filePath) {
        var data = await readCsvFile(filePath);
        if (data) {
            res.status(200).json(data);
        } else {
            console.warn('CSV file is empty');
            res.status(500).send('Something went wrong!');
        }
    } else {
        res.status(404).send('Resource not found');
    }
});

app.get('/', (req, res) => {
    res.redirect('/tc');
});


app.get('/tc', (req, res) => {
    res.sendFile(path.join(process.cwd(), 'public', 'tc.html'));
});

app.use((err, req, res, next) => {
    console.error("Req" + req);
    console.error(err.stack);
    res.status(500).send('Something went wrong!');
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});


// Export for server build
module.exports = app;