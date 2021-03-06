option = {
    textStyle: {
        fontSize: 16,
    },
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],
    title: {
        text: 'mssql-100并发'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data: ['ssi', '读未提交隔离级别+Java锁', '读已提交隔离级别+Java锁', '可重复读隔离级别+Java锁', '读未提交隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '可重复读隔离级别+Redis锁']
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '10%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'category',
        boundaryGap: false,
        data: ['1.1', '1.2', '1.3', '1.4', '1.5'],
        name: 'zipf系数'
    },
    yAxis: {
        axisLabel: {
            fontSize: 16,
        },
        type: 'value',
        min: 20,
        max: 100,
        name: '吞吐量(qps)'
    },
    series: [
        {
            name: 'ssi',
            type: 'line',

            data: [45.375, 36.50747, 34.69635, 32.45293, 31.09414]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',

            data: [85.590584, 71.37804, 64.1102, 57.59453, 56.27744]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',

            data: [84.5154, 70.51714, 62.4232, 57.14925, 55.09404]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',

            data: [82.32675, 69.5906, 61.91328, 56.72885, 52.25616]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',

            data: [79.62474, 65.4992, 57.98806, 54.1632, 51.70088]
        },

        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',

            data: [77.48802, 61.74526, 57.69876, 53.83026, 51.21594]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',

            data: [65.94419, 55.24524, 53.60356, 48.6758, 44.04704]
        },
    ]
};