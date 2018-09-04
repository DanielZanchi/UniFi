% esempio con f(x) = (pi*x)/(x+1)
xi = [0,1,2,3];
fi = [0, 1.570796, 2.094395, 2.3561944];
x = 1.5;

% not-a-knot
y_nan = spline3(xi, fi, x, true);
% naturale
y_nat = spline3(xi, fi, x, false);
y = (pi*x)/(x + 1);


function y = spline3(xi, fi, x, isNotAKnot)
% function y = spline3(xi, fi, x, isNotAKnot)
% xi vettore delle ascisse
% fi vettore delle valutazioni di f(x)
% punto da valutare
% isNotAKnot true se not-a-knot, false se naturale
% y risultato approssimazione di f(x)
    s = p_spline3(xi, fi, isNotAKnot);
    n = 0;
    for i = 1:xi(length(xi))
        if x > xi(i) && x <= xi(i+1)
            n = i;
            break
        end
    end

    y = double(subs(s(n), x));
end

function s = p_spline3(xi, fi, tipo)
    n = length(xi) - 1;
    xis = zeros(1, n - 1);
    phi = zeros(1, n - 1);
    for i = 1 : n - 1
        phi(i) = ( xi(i + 1) - xi(i) ) / ( xi(i + 2) - xi(i) );
        xis(i) = ( xi(i + 2) - xi(i + 1) ) / ( xi(i + 2) - xi(i) );
    end
    dd = diff_div_spline3(xi, fi);
    if tipo
        m = vettore_sistema_spline3(phi, xis, dd);
    else
        m = sistema_spline3(phi, xis, dd);
    end

    s = espressione_spline3(xi, fi, m);
end

function fi = diff_div_spline3(xi, fi)

    n = length(xi) - 1;

    for j = 1 : 2
        for i = n + 1 : - 1 : j + 1
            fi(i) = ( fi(i) - fi(i - 1) )/(xi(i) - xi(i - j) );
        end
    end

    fi = fi(3 : length(fi))';
end

function m = sistema_spline3(phi, xi, dd)
    n = length(xi) + 1;
    u = zeros(1, n - 1);
    l = zeros(1, n - 2);
    u(1) = 2;
    for i = 2 : n - 1
        l(i) = phi(i) / u(i - 1);
        u(i) = 2 - l(i) * xi(i - 1);
    end
    dd = 6 * dd;
    y = zeros(1, n - 1);
    y(1) = dd(1);
    for i = 2 : n - 1
        y(i) = dd(i) - l(i) * y(i - 1);
    end
    m = zeros(1, n - 1);
    m(n - 1) = y(n - 1) / u(n - 1);
    for i = n - 2 : - 1 : 1
        m(i) = (y(i) - xi(i) * m(i + 1)) / u(i);
    end
    m = [0 m 0];
end

function m = vettore_sistema_spline3(phi, xi, dd)
    n = length(xi) + 1;
    if n + 1 < 4
        error('Not-A-Knot con meno di 4 ascisse!');
    end
    dd = [6 * dd(1); 6 * dd; 6 * dd(length(dd))];
    w = zeros(n, 1);
    u = zeros(n + 1, 1);
    l = zeros(n, 1);
    y = zeros(n + 1, 1);
    m = zeros(n + 1, 1);
    u(1) = 1;
    w(1) = 0;
    l(1) = phi(1);
    u(2) = 2 - phi(1);
    w(2) = xi(1) - phi(1);
    l(2) = phi(2) / u(2);
    u(3) = 2 - ( l(2) * w(2) );
    w(3) = xi(2);
    for i = 4 : n - 1
        l(i - 1) = phi(i - 1) / u(i - 1);
        u(i) = 2 - l(i - 1) * w(i - 1);
        w(i) = xi(i - 1);
    end
    l(n - 1) = ( phi(n - 1) - xi(n - 1) ) / u(n - 1);
    u(n) = 2 - xi(n - 1) - l(n - 1) * w(n - 1);
    w(n) = xi(n - 1);
    l(n) = 0;
    u(n + 1) = 1;
    y(1) = dd(1);
    for i = 2 : n + 1
        y(i) = dd(i) - l(i - 1) * y(i - 1);
    end
    m(n + 1) = y(n + 1) / u(n + 1);
    for i = n : -1 : 1
        m(i) = (y(i) - w(i) * m(i + 1))/u(i);
    end
    m(1) = m(1) - m(2) - m(3);
    m(n + 1) = m(n + 1) - m(n) - m(n - 1);
end


function s = espressione_spline3(xi, fi, m)
    n = length(xi) - 1;
    s = sym('x' , [n 1]);
    syms x;
    for i = 2 : n + 1
        hi = xi(i) - xi(i - 1);
        ri = fi(i - 1) - hi^2/6 * m(i - 1);
        qi = (fi(i) - fi(i - 1))/hi - hi/6 * (m(i) - m(i - 1));
        s(i - 1) = ( (x - xi(i - 1))^3 * m(i) + (xi(i) - x)^3 * m(i - 1) ) / (6 * hi) + qi * (x - xi(i - 1)) + ri;
    end
end
