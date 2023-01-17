if (test?.test?.test === test.test.test) {
        try {
            const test = test;
            await Promise.all([test?.test?.test, test?.test?.test].map(async (test) => {
                if (test) {
                    await test({
                        test
                    });
                }
            }));
        } catch (err) {
            test.logger.error(err);
        }
    }