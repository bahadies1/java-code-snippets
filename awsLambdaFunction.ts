
const transporter = nodemailer.createTransport({

});

exports.handler = async (event) => {

    try {

        const { data } = await axios.get(process.env.LAMBDA_TRIGGER_URL, { headers: { 'Accept-Encoding': 'gzip', token: process.env.cronKey }});
        
        const date = moment().format('DD/MM/YYYY');
        let subject = "";
        let message = "";

        const filename = '';
        let workbook = new ExcelJS.Workbook();
        let worksheet = workbook.addWorksheet('');

        worksheet.columns = [
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header:},
            {header'}

        ];

        data.forEach((e) => {
            worksheet.addRow(e);
        });

        const buffer = await workbook.xlsx.writeBuffer();

        const promises = [];

        promises.push(

            transporter.sendMail({});

        await Promise.all(promises);

        return {
            status: 200,
            body: "OK"
        }

    } catch (err) {

        console.error(err);
        return {
            status: 500,
            body: 'Fail'
        }
    }
};
