%es10

% Moto uniformemente accelerato , stima nel senzo dei minimi quadrati
% Il problema e ben posto essendo n = 3 e e il numero di ascisse é almeno
% n+1


format longg


misurazioni = [1 2.9;
1 3.1;
2 6.9;
2 7.1;
3 12.9;
3 13.1;
4 20.9;
4 21.1;
5 30.9;
5 31.1];


n=2;


A = vander(misurazioni(:,1));

A = A(:,8:10)

A = fliplr(A);
misurazioni(:,2);
xx = soluzione_es_8(algoritmo_3_8(A),misurazioni(:,2))

poly = polyfit(misurazioni(:,1),misurazioni(:,2),2)


% fattorizzazione QR di householder
function A = algoritmo_3_8(A)
    [m,n] = size(A);
    for i=1:n
        alpha = norm(A(i:m, i));
        if alpha==0
            error("il rango non e' massimo")
        end
        if A(i,i)>=0
            alpha = -alpha;
        end
        v = A(i,i) - alpha;
        A(i,i) = alpha;
        A(i+1:m,i) = A(i+1:m,i)/v;
        beta = -v/alpha;
        A(i:m,i+1:n) = A(i:m, i+1:n) - (beta*[1; A(i+1:m,i)])*([1 A(i+1:m,i)']*A(i:m,i+1:n));
    end
end

function [b] = soluzione_es_8(A, b)
    [m,n] = size(A);
    Qt = eye(m);
    for i=1:n
        Qt= [eye(i-1) zeros(i-1,m-i+1); zeros(i-1, m-i+1)' (eye(m-i+1)-(2/norm([1; A(i+1:m, i)], 2)^2)*([1; A(i+1:m, i)]*[1 A(i+1:m, i)']))]*Qt;
    end
    b = sistema_triang_sup(triu(A(1:n, :)), Qt(1:n, :)*b);
end

function [b] = sistema_triang_sup(A, b)
	for j=length(A):-1:1
		b(j)=b(j)/A(j,j);
		for i=1:j-1
			b(i)=b(i)-A(i,j)*b(j);
		end
	end
end
