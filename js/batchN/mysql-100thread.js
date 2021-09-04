option = {
    color: ["#333", "#e01f54", '#e87c25', '#fcce10', '#3fb1e3', '#6be6c1', '#c4ebad'],

    // title: {
    //     text: 'MySQL事务的吞吐量趋势',
    //     left: 'center',
    // },

    tooltip: {
        trigger: 'axis'
    },

    legend: {
        top: ' 0%',
        data: ['可串行化隔离级别', '可重复读隔离级别+Redis锁', '读已提交隔离级别+Redis锁', '读未提交隔离级别+Redis锁', '可重复读隔离级别+Java锁', '读已提交隔离级别+Java锁', '读未提交隔离级别+Java锁']
    },

    grid: {
        top: '10%',
        left: '3%',
        right: '15%',
        bottom: '3%',
        containLabel: true
    },

    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['1', '10', '20'],
        name: '事务中锁定资源（个）'
    },
    yAxis: {
        type: 'value',
        min: 50,
        max: 280,
        name: '吞吐量(qps)'
    },

    series: [
        {
            name: '可串行化隔离级别',
            type: 'line',
            data: [270.29619,95.43096,60.74598]
        },
        {
            name: '可重复读隔离级别+Redis锁',
            type: 'line',
            data: [226.7,100.85193, 62.38956]
        },
        {
            name: '读已提交隔离级别+Redis锁',
            type: 'line',
            data: [236.9,104.3058, 63.34016]
        },
        {
            name: '读未提交隔离级别+Redis锁',
            type: 'line',
            data: [240.1,104.76576,63.90982 ]
        },
        {
            name: '可重复读隔离级别+Java锁',
            type: 'line',
            data: [258.5,121.2, 74.69223]
        },
        {
            name: '读已提交隔离级别+Java锁',
            type: 'line',
            data: [265,124.4, 77.1]
        },
        {
            name: '读未提交隔离级别+Java锁',
            type: 'line',
            data: [266.3,126.3, 77.33808]
        },
    ]
};