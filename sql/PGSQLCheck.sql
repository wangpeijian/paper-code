-- 验证库存补货/购买操作是否正确
SELECT
	p.stock,
	coalesce( rls.reload, 0 ) as reload,
	coalesce( ois.cus, 0 ) as cus,
	1000000 AS total,
	(p.stock + coalesce( ois.cus, 0 ) - coalesce( rls.reload, 0 ) = 1000000) AS equals
FROM
	product AS p
	LEFT JOIN ( SELECT oi.product_id, SUM( oi.num ) AS cus FROM order_item AS oi GROUP BY oi.product_id ) AS ois ON ois.product_id = p.id
	LEFT JOIN ( SELECT rl.product_id, SUM( rl.num ) AS reload FROM reload_log AS rl GROUP BY rl.product_id ) AS rls ON rls.product_id = p.id
WHERE
	p.id <= 1000;

-- 验证用户账户购买/充值是否正确
SELECT
	*,
	( 100000000 + 20000000 + cashRec + creditRec ) AS left,
	cashBlance + creditBlance + cashConsum + creditConsum AS right,
	100000000 + 20000000 + cashRec + creditRec = cashBlance + creditBlance + cashConsum + creditConsum AS equals
FROM
	(
	SELECT
		u.id,
		ac.cash AS cashBlance,
		acr.credit AS creditBlance,

		coalesce(t1s.cash, 0) AS cashConsum,
		coalesce(t1s.credit, 0) AS creditConsum,
		coalesce(t1s.debt, 0) AS debt,
		coalesce(t2s.cash, 0) AS cashRec,
		coalesce(t2s.credit, 0) AS creditRec

	FROM
		user_info AS u
		LEFT JOIN account_cash AS ac ON ac.id = u.id
		LEFT JOIN account_credit AS acr ON acr.id = u.id
		LEFT JOIN (
		SELECT
			t1.user_id,
			SUM( t1.cash ) AS cash,
			SUM( t1.credit ) AS credit,
			SUM( t1.debt ) AS debt
		FROM
			trade AS t1
		WHERE
			t1.type = 1
			OR t1.type = 2
		GROUP BY
			t1.user_id
		) AS t1s ON t1s.user_id = u.id
		LEFT JOIN (
		SELECT
			t2.user_id,
			SUM( t2.cash ) AS cash,
			SUM( t2.credit ) AS credit,
			SUM( t2.debt ) AS debt
		FROM
			trade AS t2
		WHERE
			t2.type = 3
		GROUP BY
			t2.user_id
		) AS t2s ON t2s.user_id = u.id
	) AS res
WHERE
	res.id <= 100;