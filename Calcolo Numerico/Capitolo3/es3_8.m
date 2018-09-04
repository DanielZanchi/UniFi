function [b] = soluzione_es_8(A, b)
    [m,n] = size(A);
    Q_t = eye(m);
    for i=1:n
        Q_t= [eye(i-1) zeros(i-1,m-i+1); zeros(i-1, m-i+1)' (eye(m-i+1)-(2/norm([1; A(i+1:m, i)], 2)^2)*([1; A(i+1:m, i)]*[1 A(i+1:m, i)']))]*Q_t;
    end
    b = sist_triang_sup(triu(A(1:n, :)), Q_t(1:n, :)*b);
end